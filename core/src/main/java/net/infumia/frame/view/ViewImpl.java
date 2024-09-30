package net.infumia.frame.view;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import net.infumia.frame.config.ViewConfigRich;
import net.infumia.frame.context.ContextBase;
import net.infumia.frame.context.view.ContextInit;
import net.infumia.frame.context.view.ContextOpen;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.context.view.ContextRenderRich;
import net.infumia.frame.metadata.MetadataKeyHolder;
import net.infumia.frame.pipeline.executor.PipelineExecutorView;
import net.infumia.frame.pipeline.executor.PipelineExecutorViewImpl;
import net.infumia.frame.service.ConsumerService;
import net.infumia.frame.slot.LayoutSlot;
import net.infumia.frame.typedkey.TypedKeyStorageImmutable;
import net.infumia.frame.util.Pair;
import net.infumia.frame.view.config.ViewConfig;
import net.infumia.frame.view.config.option.ViewConfigOptions;
import net.infumia.frame.viewer.ContextualViewer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ViewImpl implements View, ViewEventHandler {

    private final PipelineExecutorView pipelines = new PipelineExecutorViewImpl(this);
    private final Object instance;
    private final ContextInit context;

    public ViewImpl(@NotNull final ContextInit context, @NotNull final Object instance) {
        this.context = context;
        this.instance = instance;
    }

    @NotNull
    @Override
    public ContextInit context() {
        return this.context;
    }

    @NotNull
    @Override
    public Object instance() {
        return this.instance;
    }

    @NotNull
    @Override
    public PipelineExecutorView pipelines() {
        return this.pipelines;
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> simulateOnInit() {
        return this.pipelines.executeInit(this.context);
    }

    @NotNull
    @Override
    public CompletableFuture<@Nullable ContextRender> simulateOpen(
        @NotNull final Collection<Player> viewers,
        @NotNull final TypedKeyStorageImmutable initialData
    ) {
        return this.pipelines.executeCreateViewers(viewers)
            .thenCompose(v -> this.pipelines.executeCreateContext(v, initialData))
            .thenCompose(this::navigate);
    }

    @NotNull
    @Override
    public CompletableFuture<@Nullable ContextRender> simulateOpenActive(
        @NotNull final ContextRender activeContext,
        @NotNull final Collection<Player> viewers
    ) {
        return this.pipelines.executeCreateViewers(viewers)
            .thenCompose(((ContextRenderRich) activeContext)::simulateNavigate)
            .thenApply(__ -> activeContext);
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> simulateClick(
        @NotNull final ContextualViewer viewer,
        @NotNull final InventoryClickEvent event
    ) {
        return this.pipelines.executeClick(viewer, event);
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> simulateClose(
        @NotNull final ContextualViewer viewer
    ) {
        final ContextualViewer transitioning = viewer
            .metadata()
            .remove(MetadataKeyHolder.TRANSITIONING_FROM);
        final Boolean forcedClose = viewer.metadata().remove(MetadataKeyHolder.FORCED_CLOSE);
        final boolean forced = transitioning != null || (forcedClose != null && forcedClose);
        return this.pipelines.executeClose(viewer, forced);
    }

    @Override
    public void handleItemPickup(
        @NotNull final ContextualViewer viewer,
        @NotNull final PlayerPickupItemEvent event
    ) {
        final ContextRender context = viewer.context();
        final ViewConfigRich config = (ViewConfigRich) context.config();
        config
            .option(ViewConfigOptions.CANCEL_ON_PICKUP)
            .filter(l -> l)
            .ifPresent(cancel -> event.setCancelled(true));
    }

    @Override
    public void handleItemDrop(
        @NotNull final ContextualViewer viewer,
        @NotNull final PlayerDropItemEvent event
    ) {
        final ContextRender context = viewer.context();
        final ViewConfigRich config = (ViewConfigRich) context.config();
        config
            .option(ViewConfigOptions.CANCEL_ON_DROP)
            .filter(l -> l)
            .ifPresent(cancel -> event.setCancelled(true));
    }

    @Override
    public void handleInventoryDrag(
        @NotNull final ContextualViewer viewer,
        @NotNull final InventoryDragEvent event
    ) {
        final ContextRender context = viewer.context();
        final ViewConfigRich config = (ViewConfigRich) context.config();
        config
            .option(ViewConfigOptions.CANCEL_ON_DRAG)
            .filter(l -> l)
            .ifPresent(cancel -> event.setCancelled(true));
    }

    @NotNull
    private CompletionStage<@Nullable ContextRender> navigate(@NotNull final ContextBase context) {
        return this.pipelines.executeOpen(context)
            .thenApply(Pair::second)
            .thenCompose(open -> {
                if (open.cancelled()) {
                    return CompletableFuture.completedFuture(null);
                } else {
                    return this.executeProcessConfigModifiers(open);
                }
            });
    }

    @NotNull
    private CompletableFuture<ContextRender> executeProcessConfigModifiers(
        @NotNull final ContextOpen context
    ) {
        return this.pipelines.executeProcessConfigModifiers(context).thenCompose(__ ->
                this.executeCreateContainer(context, context.buildFinalConfig())
            );
    }

    @NotNull
    private CompletableFuture<ContextRender> executeCreateContainer(
        @NotNull final ContextBase context,
        @NotNull final ViewConfig config
    ) {
        return this.pipelines.executeCreateContainer(context, config)
            .thenApply(ViewContainerRich.class::cast)
            .thenCompose(container -> this.executeModifyContainer(context, config, container));
    }

    @NotNull
    private CompletableFuture<ContextRender> executeModifyContainer(
        @NotNull final ContextBase context,
        @NotNull final ViewConfig config,
        @NotNull final ViewContainer container
    ) {
        return this.pipelines.executeModifyContainer(context, config, container).thenCompose(pair ->
                this.executeLayoutResolution(context, config, pair.second().container())
            );
    }

    @NotNull
    private CompletableFuture<ContextRender> executeLayoutResolution(
        @NotNull final ContextBase context,
        @NotNull final ViewConfig config,
        @NotNull final ViewContainer container
    ) {
        return this.pipelines.executeLayoutResolution(context, config, container).thenCompose(
                pair -> this.executeCreateRender(context, config, container, pair.second())
            );
    }

    @NotNull
    private CompletableFuture<ContextRender> executeCreateRender(
        @NotNull final ContextBase context,
        @NotNull final ViewConfig config,
        @NotNull final ViewContainer container,
        @NotNull final Map<Character, LayoutSlot> layouts
    ) {
        return this.pipelines.executeCreateRender(context, config, container, layouts).thenCompose(
                render -> ((ContextRenderRich) render).simulateFirstRender().thenApply(__ -> render)
            );
    }
}
