package net.infumia.frame.element.pagination;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import net.infumia.frame.Preconditions;
import net.infumia.frame.context.ContextBase;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.element.Element;
import net.infumia.frame.element.ElementEventHandler;
import net.infumia.frame.element.ElementImpl;
import net.infumia.frame.element.ElementRich;
import net.infumia.frame.element.item.ElementItem;
import net.infumia.frame.element.item.ElementItemBuilder;
import net.infumia.frame.element.item.ElementItemBuilderImpl;
import net.infumia.frame.element.item.ElementItemBuilderRich;
import net.infumia.frame.pipeline.executor.PipelinesElement;
import net.infumia.frame.pipeline.executor.PipelinesElementImpl;
import net.infumia.frame.service.ConsumerService;
import net.infumia.frame.slot.LayoutSlot;
import net.infumia.frame.state.State;
import net.infumia.frame.state.pagination.PaginationElementConfigurer;
import net.infumia.frame.view.ViewContainer;
import org.jetbrains.annotations.NotNull;

public final class ElementPaginationImpl<T> extends ElementImpl implements ElementPaginationRich {

    private final PipelinesElement pipelines = new PipelinesElementImpl(this);
    private final ElementEventHandler eventHandler = ElementEventHandlerPagination.INSTANCE;
    private LayoutSlot currentLayoutSlot;
    private final SourceProvider<T> sourceProvider;
    private final char layout;
    private final BiConsumer<ContextBase, ElementPagination> onPageSwitch;
    private final PaginationElementConfigurer<T> elementConfigurer;
    private final boolean initiateEagerly;
    private final State<ElementPagination> associated;
    private final Function<ContextBase, CompletableFuture<List<T>>> sourceFactory;
    private final Function<List<T>, List<T>> pageCalculation;
    private final Executor continuationExecutor;
    private List<Element> elements = new ArrayList<>();
    private int currentPageIndex;
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
        this.initiateEagerly = builder.initiateEagerly;
        this.sourceProvider = builder.sourceProvider;
        this.layout = builder.layout;
        this.onPageSwitch = builder.onPageSwitch;

        if (this.sourceProvider instanceof SourceProvider.Immutable) {
            this.currentSource = this.sourceProvider.apply(this.parent).join();
            this.sourceFactory = null;
        } else {
            this.sourceFactory = this.sourceProvider;
        }

        if (this.sourceProvider.async()) {
            this.continuationExecutor = parent.frame().taskFactory().asExecutor();
        } else {
            this.continuationExecutor = Runnable::run;
        }

        this.pageCalculation = result -> {
            this.loading = false;
            this.currentSource = result;
            this.pageCount = this.calculatePagesCount(result);
            final int lastOrCurrentPage = Math.min(this.currentPageIndex, this.lastPageIndex());
            if (lastOrCurrentPage != this.currentPageIndex) {
                this.switchTo(lastOrCurrentPage);
            }
            return ElementPaginationImpl.splitSourceForPage(
                this.currentPageIndex,
                this.pageSize(),
                this.pageCount,
                result
            );
        };
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
        return this.elements;
    }

    @Override
    public void clearElements() {
        this.elements = new ArrayList<>();
    }

    @NotNull
    @Override
    public CompletableFuture<?> initiate(@NotNull final ContextBase context) {
        return this.loadSourceForTheCurrentPage(context, true);
    }

    @Override
    public char layout() {
        return this.layout;
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
        return Math.max(0, this.currentPageIndex - 1);
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
        this.currentPageIndex = Math.max(0, pageIndex);
        this.pageWasChanged = true;
        this.parent.frame()
            .loggedFuture(
                this.update()
                    .thenRun(() -> {
                        final ContextRender host = (ContextRender) this.parent;
                        if (this.onPageSwitch != null) {
                            this.onPageSwitch.accept(host, this);
                        }
                    }),
                "An error occurred while updating the pagination '%s'.",
                this
            );
    }

    @Override
    public void advance() {
        if (this.canAdvance()) {
            this.switchTo(this.nextPageIndex());
        }
    }

    @Override
    public boolean canAdvance() {
        return this.hasPage(this.currentPageIndex + 1);
    }

    @Override
    public void back() {
        if (this.canBack()) {
            this.switchTo(this.previousPageIndex());
        }
    }

    @Override
    public boolean canBack() {
        return this.hasPage(this.currentPageIndex - 1);
    }

    @Override
    public boolean isInitiateEagerly() {
        return this.initiateEagerly;
    }

    @NotNull
    @Override
    public ElementEventHandler eventHandler() {
        return this.eventHandler;
    }

    @Override
    public void visible(final boolean visible) {
        super.visible(visible);
        for (final Element child : this.elements) {
            ((ElementRich) child).visible(visible);
        }
    }

    @Override
    public boolean containedWithin(final int position) {
        if (this.currentLayoutSlot != null) {
            return Arrays.stream(this.currentLayoutSlot.slots()).anyMatch(slot -> slot == position);
        }
        return this.elements.stream()
            .anyMatch(element -> ((ElementRich) element).containedWithin(position));
    }

    @Override
    public boolean intersects(@NotNull final Element element) {
        for (final Element child : this.elements) {
            final ElementRich e = (ElementRich) child;
            if (e.intersects(element)) {
                return true;
            }
            if (e instanceof ElementItem) {
                final int slot = ((ElementItem) e).slot();
                if (this.currentLayoutSlot != null) {
                    return Arrays.stream(this.currentLayoutSlot.slots()).anyMatch(s -> s == slot);
                }
                return e.containedWithin(slot);
            }
        }
        return false;
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
    public PipelinesElement pipelines() {
        return this.pipelines;
    }

    @NotNull
    @Override
    public List<Element> elements() {
        return Collections.unmodifiableList(this.elements);
    }

    private void addComponentsForUnconstrainedPagination(
        @NotNull final ContextRender context,
        @NotNull final List<T> contents
    ) {
        final ViewContainer container = context.container();
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
                final IntFunction<ElementItemBuilder> builder = layoutSlot.builderFactory();
                if (builder != null) {
                    final ElementItemBuilderRich elementBuilder =
                        (ElementItemBuilderRich) builder.apply(index);
                    elementBuilder.slot(slot);
                    this.elements.add(elementBuilder.build(context));
                    index++;
                    continue;
                }
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
        final boolean lazy = this.sourceProvider.lazy();
        final boolean initialized = lazy && this.initialized;
        final boolean reuse = this.sourceProvider.provided() || initialized;
        final boolean notComputing = !this.sourceProvider.computed();
        if (reuse && notComputing && !forced) {
            final List<T> currentSource = Preconditions.stateNotNull(
                this.currentSource,
                "Current source cannot be null in this stage"
            );

            if (!lazy) {
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
        return this.sourceFactory.apply(context)
            .thenApply(this.pageCalculation)
            .thenApplyAsync(Function.identity(), this.continuationExecutor);
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
            .max(Comparator.comparing(LayoutSlot::isDefinedByUser))
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

        if (index < 0 || (pagesCount > 0 && index >= pagesCount)) {
            throw new IndexOutOfBoundsException(
                String.format(
                    "Page index must be between the range of 0 and %d. Given: %d",
                    pagesCount - 1,
                    index
                )
            );
        }

        final int fromIndex = index * pageSize;
        final int toIndex = Math.min(fromIndex + pageSize, src.size());
        return src.subList(fromIndex, toIndex);
    }
}
