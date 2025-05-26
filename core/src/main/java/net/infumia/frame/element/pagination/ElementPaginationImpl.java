package net.infumia.frame.element.pagination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.infumia.frame.Preconditions;
import net.infumia.frame.context.ContextBase;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.element.Element;
import net.infumia.frame.element.ElementEventHandler;
import net.infumia.frame.element.ElementImpl;
import net.infumia.frame.element.ElementRich;
import net.infumia.frame.element.item.ElementItem;
import net.infumia.frame.element.item.ElementItemBuilderImpl;
import net.infumia.frame.element.item.ElementItemBuilderRich;
import net.infumia.frame.pipeline.executor.PipelineExecutorElement;
import net.infumia.frame.pipeline.executor.PipelineExecutorElementImpl;
import net.infumia.frame.service.ConsumerService;
import net.infumia.frame.slot.LayoutSlot;
import net.infumia.frame.state.State;
import net.infumia.frame.state.pagination.PaginationElementConfigurer;
import net.infumia.frame.state.pagination.StatePagination;
import net.infumia.frame.view.ViewContainer;
import org.jetbrains.annotations.NotNull;

public final class ElementPaginationImpl<T> extends ElementImpl implements ElementPaginationRich {

    private final ReadWriteLock elementLock = new ReentrantReadWriteLock();
    private final PipelineExecutorElement pipelines = new PipelineExecutorElementImpl(this);
    private final ElementEventHandler eventHandler = ElementEventHandlerPagination.INSTANCE;
    private LayoutSlot currentLayoutSlot;
    final SourceProvider<T> sourceProvider;
    final Function<ElementPaginationBuilder<T>, StatePagination> stateFactory;
    final char layout;
    final BiConsumer<ContextBase, ElementPagination> onPageSwitch;
    final PaginationElementConfigurer<T> elementConfigurer;
    final State<ElementPagination> associated;
    private final Function<ContextBase, CompletableFuture<List<T>>> sourceFactory;
    private List<Element> elements = new ArrayList<>();
    private int currentPageIndex = 0;
    private boolean pageWasChanged;
    private boolean initialized = false;
    private int pageCount;
    private List<T> currentSource;
    private boolean loading;
    private int pageSize = -1;

    ElementPaginationImpl(
        @NotNull final ElementPaginationBuilderImpl<T> builder,
        @NotNull final ContextBase parent
    ) {
        super(builder, parent);
        this.associated = Preconditions.argumentNotNull(
            builder.associated,
            "Associated state cannot be null for ElementPagination!"
        );
        this.elementConfigurer = Preconditions.argumentNotNull(
            builder.elementConfigurer,
            "Element configurer cannot be null for ElementPagination!"
        );
        this.sourceProvider = builder.sourceProvider;
        this.stateFactory = builder.stateFactory;
        this.layout = builder.layout;
        this.onPageSwitch = builder.onPageSwitch;

        if (this.sourceProvider instanceof SourceProvider.Immutable) {
            this.currentSource = this.sourceProvider.apply(this.parent).join();
            this.sourceFactory = null;
        } else {
            this.sourceFactory = this.sourceProvider;
        }
    }

    @NotNull
    @Override
    public State<ElementPagination> associated() {
        return this.associated;
    }

    @Override
    public boolean pageWasChanged() {
        return this.pageWasChanged;
    }

    @Override
    public void pageWasChanged(final boolean pageWasChanged) {
        this.pageWasChanged = pageWasChanged;
    }

    @Override
    public boolean initialized() {
        return this.initialized;
    }

    @Override
    public void initialized(final boolean initialized) {
        this.initialized = initialized;
    }

    @Override
    public void updatePageSize(@NotNull final ContextRender context) {
        final String[] layout = context.config().layout();
        if (layout == null) {
            this.pageSize = context.container().size();
        } else {
            this.pageSize = this.layoutSlotFor(context).slots().length;
        }
    }

    @NotNull
    @Override
    public CompletableFuture<?> loadCurrentPage(
        @NotNull final ContextRender context,
        final boolean forced
    ) {
        return this.loadSourceForTheCurrentPage(context, forced).thenAccept(pageContents -> {
                if (pageContents.isEmpty()) {
                    return;
                }
                if (context.layouts().isEmpty()) {
                    this.addComponentsForUnconstrainedPagination(context, pageContents);
                } else {
                    this.addComponentsForLayeredPagination(context, pageContents);
                }
            });
    }

    @NotNull
    @Override
    public Collection<Element> modifiableElements() {
        try {
            this.elementLock.readLock().lock();
            return this.elements;
        } finally {
            this.elementLock.readLock().unlock();
        }
    }

    @Override
    public void clearElements() {
        try {
            this.elementLock.writeLock().lock();
            this.elements = new ArrayList<>();
        } finally {
            this.elementLock.writeLock().unlock();
        }
    }

    @Override
    public int currentPageIndex() {
        return this.currentPageIndex;
    }

    @Override
    public int nextPageIndex() {
        return Math.max(0, Math.min(this.pageCount, this.currentPageIndex + 1));
    }

    @Override
    public int previousPageIndex() {
        return Math.max(0, Math.min(this.pageCount, this.currentPageIndex - 1));
    }

    @Override
    public int lastPageIndex() {
        return Math.max(0, this.pageCount - 1);
    }

    @Override
    public boolean isFirstPage() {
        return this.currentPageIndex == 0;
    }

    @Override
    public boolean isLastPage() {
        return !this.canAdvance();
    }

    @Override
    public int elementCount() {
        return this.currentSource == null ? 0 : this.currentSource.size();
    }

    @Override
    public int pageCount() {
        return this.pageCount;
    }

    @Override
    public boolean hasPage(final int pageIndex) {
        if (this.sourceProvider.computed()) {
            return true;
        } else if (pageIndex < 0) {
            return false;
        } else {
            return pageIndex < this.pageCount;
        }
    }

    @Override
    public void switchTo(final int pageIndex) {
        Preconditions.state(
            this.hasPage(pageIndex),
            "Page index not found (%d > %d)",
            pageIndex,
            this.pageCount
        );
        if (this.loading) {
            return;
        }
        this.currentPageIndex = pageIndex;
        this.pageWasChanged = true;
        final ContextRender host = (ContextRender) this.parent;
        if (this.onPageSwitch != null) {
            this.onPageSwitch.accept(host, this);
        }
        this.parent.frame()
            .loggedFuture(
                this.update(),
                "An error occurred while updating the pagination '%s'.",
                this
            );
    }

    @Override
    public void advance() {
        if (this.canAdvance()) {
            this.switchTo(this.currentPageIndex + 1);
        }
    }

    @Override
    public boolean canAdvance() {
        return this.hasPage(this.currentPageIndex + 1);
    }

    @Override
    public void back() {
        if (this.canBack()) {
            this.switchTo(this.currentPageIndex - 1);
        }
    }

    @Override
    public boolean canBack() {
        return this.hasPage(this.currentPageIndex - 1);
    }

    @NotNull
    @Override
    public ElementEventHandler eventHandler() {
        return this.eventHandler;
    }

    @Override
    public void visible(final boolean visible) {
        try {
            this.elementLock.readLock().lock();
            super.visible(visible);
            for (final Element child : this.elements) {
                ((ElementRich) child).visible(visible);
            }
        } finally {
            this.elementLock.readLock().unlock();
        }
    }

    @Override
    public boolean containedWithin(final int position) {
        try {
            this.elementLock.readLock().lock();
            if (this.currentLayoutSlot != null) {
                return Arrays.stream(this.currentLayoutSlot.slots()).anyMatch(
                    slot -> slot == position
                );
            }
            return this.elements.stream()
                .anyMatch(element -> ((ElementRich) element).containedWithin(position));
        } finally {
            this.elementLock.readLock().unlock();
        }
    }

    @Override
    public boolean intersects(@NotNull final Element element) {
        try {
            this.elementLock.readLock().lock();
            for (final Element child : this.elements) {
                final ElementRich e = (ElementRich) child;
                if (e.intersects(element)) {
                    return true;
                }
                if (e instanceof ElementItem) {
                    final int slot = ((ElementItem) e).slot();
                    if (this.currentLayoutSlot != null) {
                        return Arrays.stream(this.currentLayoutSlot.slots()).anyMatch(
                            s -> s == slot
                        );
                    }
                    return e.containedWithin(slot);
                }
            }
            return false;
        } finally {
            this.elementLock.readLock().unlock();
        }
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> update() {
        Preconditions.state(
            this.parent instanceof ContextRender,
            "You cannot update the element '%s' when the parent is not a ContextRender!",
            this
        );
        return this.pipelines.executeUpdate((ContextRender) this.parent, false);
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> forceUpdate() {
        Preconditions.state(
            this.parent instanceof ContextRender,
            "You cannot update the element '%s' when the parent is not a ContextRender!",
            this
        );
        return this.pipelines.executeUpdate((ContextRender) this.parent, true);
    }

    @NotNull
    @Override
    public PipelineExecutorElement pipelines() {
        return this.pipelines;
    }

    @NotNull
    @Override
    public List<Element> elements() {
        try {
            this.elementLock.readLock().lock();
            return Collections.unmodifiableList(this.elements);
        } finally {
            this.elementLock.readLock().unlock();
        }
    }

    private void addComponentsForUnconstrainedPagination(
        @NotNull final ContextRender context,
        @NotNull final List<T> contents
    ) {
        final ViewContainer container = context.container();

        /*if (this.pageSize == -1) {
            this.updatePageSize(context);
        }*/

        final int lastSlot = Math.min(container.lastSlot() + 1, contents.size());
        int index = 0;
        for (int slot = container.firstSlot(); slot < lastSlot; slot++) {
            final T value = contents.get(slot);
            final ElementItemBuilderRich builder = new ElementItemBuilderImpl();
            builder.root(this);
            builder.slot(slot);
            this.elementConfigurer.configure(context, builder, index++, slot, value);
            this.elements.add(builder.build(context));
        }
    }

    private void addComponentsForLayeredPagination(
        @NotNull final ContextRender context,
        @NotNull final List<T> contents
    ) {
        final LayoutSlot layoutSlot = this.layoutSlotFor(context);
        final int elementCount = contents.size();
        int index = 0;
        for (final int slot : layoutSlot.slots()) {
            if (index >= elementCount) {
                break;
            }
            final T value = contents.get(index);
            final ElementItemBuilderRich builder = new ElementItemBuilderImpl();
            builder.root(this);
            builder.slot(slot);
            this.elementConfigurer.configure(context, builder, index++, slot, value);
            this.elements.add(builder.build(context));
        }
    }

    @NotNull
    private CompletableFuture<List<T>> loadSourceForTheCurrentPage(
        @NotNull final ContextBase context,
        final boolean forced
    ) {
        final boolean isLazy = this.sourceProvider.lazy();
        final boolean reuseLazy = isLazy && this.initialized;
        if (
            (this.sourceProvider.provided() || reuseLazy) &&
            !this.sourceProvider.computed() &&
            !forced
        ) {
            final List<T> currentSource = Preconditions.stateNotNull(
                this.currentSource,
                "Current source cannot be null in this stage"
            );

            if (!isLazy) {
                this.pageCount = this.calculatePagesCount(currentSource);
            }

            return CompletableFuture.completedFuture(
                ElementPaginationImpl.splitSourceForPage(
                    this.currentPageIndex,
                    this.pageSize(),
                    this.pageCount,
                    currentSource
                )
            );
        }

        this.loading = true;
        if (this.sourceFactory == null) {
            return CompletableFuture.completedFuture(Collections.emptyList());
        }
        return this.sourceFactory.apply(context).thenApply(result -> {
                this.currentSource = result;
                this.pageCount = this.calculatePagesCount(result);
                this.loading = false;
                final int previousPage = Math.min(this.currentPageIndex, this.pageCount - 1);
                if (previousPage != this.currentPageIndex) {
                    this.switchTo(previousPage);
                }
                return isLazy
                    ? ElementPaginationImpl.splitSourceForPage(
                        this.currentPageIndex,
                        this.pageSize(),
                        this.pageCount,
                        result
                    )
                    : result;
            });
    }

    private int calculatePagesCount(@NotNull final List<T> source) {
        return (int) Math.ceil((double) source.size() / this.pageSize());
    }

    private int pageSize() {
        Preconditions.state(
            this.pageSize != -1,
            "Page size need to be updated before try to get it"
        );
        return this.pageSize;
    }

    @NotNull
    private LayoutSlot layoutSlotFor(@NotNull final ContextRender context) {
        if (this.currentLayoutSlot != null) {
            return this.currentLayoutSlot;
        }
        final LayoutSlot layoutSlot = context
            .layouts()
            .stream()
            .filter(slot -> slot.character() == this.layout)
            .findFirst()
            .orElseThrow(() ->
                new IllegalArgumentException(
                    String.format("Layout slot target not found: %c", this.layout)
                )
            );
        return this.currentLayoutSlot = layoutSlot;
    }

    @NotNull
    private static <T> List<T> splitSourceForPage(
        final int index,
        final int pageSize,
        final int pagesCount,
        @NotNull final List<T> src
    ) {
        if (src.isEmpty()) {
            return Collections.emptyList();
        }
        if (src.size() <= pageSize) {
            return new ArrayList<>(src);
        }
        if (index < 0 || (pagesCount > 0 && index >= pagesCount)) {
            throw new IndexOutOfBoundsException(
                String.format(
                    "Page index must be between the range of 0 and %d. Given: %d",
                    pagesCount - 1,
                    index
                )
            );
        }

        final List<T> contents = new LinkedList<>();
        final int base = index * pageSize;
        int until = base + pageSize;
        if (until > src.size()) {
            until = src.size();
        }

        for (int i = base; i < until; i++) {
            contents.add(src.get(i));
        }

        return contents;
    }
}
