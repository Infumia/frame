package net.infumia.frame.context.view;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import net.infumia.frame.Preconditions;
import net.infumia.frame.context.ContextBase;
import net.infumia.frame.context.ContextBaseImpl;
import net.infumia.frame.element.Element;
import net.infumia.frame.element.item.ElementItemBuilder;
import net.infumia.frame.element.item.ElementItemBuilderImpl;
import net.infumia.frame.element.item.ElementItemBuilderRich;
import net.infumia.frame.extension.CompletableFutureExtensions;
import net.infumia.frame.metadata.MetadataAccess;
import net.infumia.frame.metadata.MetadataKeyHolder;
import net.infumia.frame.pipeline.executor.PipelineExecutorRender;
import net.infumia.frame.pipeline.executor.PipelineExecutorRenderImpl;
import net.infumia.frame.pipeline.executor.PipelineExecutorViewer;
import net.infumia.frame.pipeline.executor.PipelineExecutorViewerImpl;
import net.infumia.frame.service.ConsumerService;
import net.infumia.frame.slot.LayoutSlot;
import net.infumia.frame.slot.SlotFinder;
import net.infumia.frame.view.ViewContainer;
import net.infumia.frame.view.config.ViewConfig;
import net.infumia.frame.viewer.Viewer;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ContextRenderImpl extends ContextBaseImpl implements ContextRenderRich {

    private final PipelineExecutorRender pipelines;
    private final PipelineExecutorViewer pipelinesViewer;
    private final SlotFinder slotFinder;
    private final List<Element> elements;
    private final ViewContainer container;
    private final ViewConfig config;
    private final Collection<LayoutSlot> layouts;
    private Closeable updateTask;

    public ContextRenderImpl(
        @NotNull final ContextBase context,
        @NotNull final ViewContainer container,
        @NotNull final ViewConfig config,
        @NotNull final Collection<LayoutSlot> layouts
    ) {
        super(context);
        this.container = container;
        this.config = config;
        this.layouts = new ArrayList<>(layouts);
        this.slotFinder = new SlotFinder(this);
        this.elements = new ArrayList<>();
        this.pipelines = new PipelineExecutorRenderImpl(this);
        this.pipelinesViewer = new PipelineExecutorViewerImpl(this);
    }

    public ContextRenderImpl(@NotNull final ContextRender context) {
        super(context);
        this.container = context.container();
        this.config = context.config();
        this.layouts = new ArrayList<>(context.layouts());
        this.slotFinder = ((ContextRenderRich) context).slotFinder();
        this.elements = new ArrayList<>(context.elements());
        this.pipelines = context.pipelines();
        this.pipelinesViewer = context.pipelinesViewer();
        this.updateTask = ((ContextRenderRich) context).updateTask();
    }

    @NotNull
    @Override
    public ViewContainer container() {
        return this.container;
    }

    @NotNull
    @Override
    public ViewConfig config() {
        return this.config;
    }

    @NotNull
    @Override
    public Collection<LayoutSlot> layouts() {
        return Collections.unmodifiableCollection(this.layouts);
    }

    @Override
    public void back() {
        final Viewer viewer = this.viewerOrThrow("back");
        final MetadataAccess metadata = viewer.metadata();
        final Deque<ContextRender> previousContexts = metadata.get(
            MetadataKeyHolder.PREVIOUS_VIEWS
        );
        if (previousContexts == null) {
            return;
        }
        final ContextRender previousContext = previousContexts.pollLast();
        if (previousContext == null) {
            metadata.remove(MetadataKeyHolder.PREVIOUS_VIEWS);
            return;
        }
        if (previousContexts.isEmpty()) {
            metadata.remove(MetadataKeyHolder.PREVIOUS_VIEWS);
        }
        CompletableFutureExtensions.logError(
            this.frame()
                .openActive(viewer.player(), previousContext)
                .thenCompose(__ ->
                    ((ContextRenderRich) previousContext).simulateResume(
                            this,
                            Collections.singleton(viewer)
                        )
                ),
            this.frame().logger(),
            "An error occurred while going back to view '%s'.",
            previousContext.view().instance()
        );
    }

    @Override
    public boolean canBack() {
        final Deque<ContextRender> previousViews =
            this.viewerOrThrow("canBack").metadata().get(MetadataKeyHolder.PREVIOUS_VIEWS);
        return previousViews != null && !previousViews.isEmpty();
    }

    @Override
    public void closeForEveryone() {
        this.closeForEveryone(true);
    }

    @Override
    public void closeForViewer() {
        this.closeForViewer(true);
    }

    @Override
    public void closeForEveryone(final boolean forced) {
        for (final Viewer viewer : this.viewers()) {
            viewer.metadata().setFixed(MetadataKeyHolder.FORCED_CLOSE, true);
            viewer.close();
        }
    }

    @Override
    public void closeForViewer(final boolean forced) {
        Preconditions.state(
            !this.sharedView(),
            "You cannot use #closeForViewer() method if it's a shared view!"
        );
        final Viewer viewer = this.viewer();
        if (forced) {
            viewer.metadata().setFixed(MetadataKeyHolder.FORCED_CLOSE, true);
        }
        viewer.close();
    }

    @NotNull
    @Override
    public PipelineExecutorViewer pipelinesViewer() {
        return this.pipelinesViewer;
    }

    @NotNull
    @Override
    public SlotFinder slotFinder() {
        return this.slotFinder;
    }

    @Override
    public void updateTask(@Nullable final Closeable task) {
        this.updateTask = task;
    }

    @Nullable
    @Override
    public Closeable updateTask() {
        return this.updateTask;
    }

    @Override
    public void addElement(@NotNull final Element element) {
        this.elements.add(element);
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> simulateFirstRender() {
        return this.pipelines.executeFirstRender()
            .thenCompose(__ -> this.simulateNavigate(this.viewers()));
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> simulateNavigate(
        @NotNull final Collection<Viewer> viewers
    ) {
        return this.pipelines.executeStartTransition(viewers)
            .thenCompose(__ -> this.pipelinesViewer.executeAdded(viewers))
            .thenCompose(__ -> this.pipelines.executeOpenContainer(viewers))
            .thenCompose(__ -> this.pipelines.executeStartUpdate());
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> simulateResume(
        @NotNull final ContextRender from,
        @NotNull final Collection<Viewer> viewers
    ) {
        return this.pipelines.executeResume(from, viewers);
    }

    @NotNull
    @Override
    public PipelineExecutorRender pipelines() {
        return this.pipelines;
    }

    @NotNull
    @Override
    public List<Element> elements() {
        return Collections.unmodifiableList(this.elements);
    }

    @NotNull
    @Override
    public ElementItemBuilder unsetSlot() {
        return this.newRegisteredBuilder();
    }

    @NotNull
    @Override
    public ElementItemBuilder layoutSlot(final char layout) {
        final LayoutSlot layoutSlot = Preconditions.argumentNotNull(
            this.slotFinder.findLayoutSlot(layout),
            "Missing layout character '%s'",
            layout
        );
        final ElementItemBuilder builder = this.newUnregisteredBuilder();
        this.layouts.add(layoutSlot.withBuilderFactory(value -> builder));
        return builder;
    }

    @NotNull
    @Override
    public ElementItemBuilder layoutSlot(final char layout, @NotNull final ItemStack item) {
        return this.layoutSlot(layout).item(item);
    }

    @Override
    public void layoutSlot(
        final char layout,
        @NotNull final BiConsumer<Integer, ElementItemBuilder> configurer
    ) {
        final LayoutSlot layoutSlot = Preconditions.argumentNotNull(
            this.slotFinder.findLayoutSlot(layout),
            "Missing layout character '%s'",
            layout
        );
        this.layouts.add(
                layoutSlot.withBuilderFactory(index -> {
                    final ElementItemBuilder builder = this.newUnregisteredBuilder();
                    configurer.accept(index, builder);
                    return builder;
                })
            );
    }

    @NotNull
    @Override
    public ElementItemBuilder slot(final int slot) {
        return this.unsetSlot().slot(slot);
    }

    @NotNull
    @Override
    public ElementItemBuilder position(final int row, final int column) {
        return this.slot(this.slotFinder.toSlot(row, column));
    }

    @NotNull
    @Override
    public ElementItemBuilder firstSlot() {
        return this.slot(this.slotFinder.findFirstSlot());
    }

    @NotNull
    @Override
    public ElementItemBuilder lastSlot() {
        return this.slot(this.slotFinder.findLastSlot());
    }

    @Override
    public void availableSlot(@NotNull final ItemStack item) {
        this.availableSlot((__, builder) -> builder.item(item));
    }

    @Override
    public void availableSlot(@NotNull final BiConsumer<Integer, ElementItemBuilder> configurer) {
        this.slotFinder.addAvailableSlotFinder((index, slot) -> {
                final ElementItemBuilder builder = this.newUnregisteredBuilder().slot(slot);
                configurer.accept(index, builder);
                return builder;
            });
    }

    @NotNull
    @Override
    public ElementItemBuilder resultSlot() {
        return this.slot(this.slotFinder.findResultSlot());
    }

    @NotNull
    @Override
    public ElementItemBuilder resultSlot(@NotNull final ItemStack item) {
        return this.resultSlot().item(item);
    }

    @NotNull
    private ElementItemBuilderRich newUnregisteredBuilder() {
        return new ElementItemBuilderImpl();
    }

    @NotNull
    private ElementItemBuilderRich newRegisteredBuilder() {
        final ElementItemBuilderRich builder = this.newUnregisteredBuilder();
        this.slotFinder.addNonRenderedBuilder(builder);
        return builder;
    }
}
