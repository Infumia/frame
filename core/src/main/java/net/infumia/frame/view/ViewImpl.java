package net.infumia.frame.view;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import net.infumia.frame.Pair;
import net.infumia.frame.config.ViewConfigRich;
import net.infumia.frame.context.ContextBase;
import net.infumia.frame.context.view.ContextInit;
import net.infumia.frame.context.view.ContextOpen;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.context.view.ContextRenderRich;
import net.infumia.frame.metadata.MetadataAccess;
import net.infumia.frame.metadata.MetadataKeyHolder;
import net.infumia.frame.pipeline.executor.PipelinesView;
import net.infumia.frame.pipeline.executor.PipelinesViewImpl;
import net.infumia.frame.service.ConsumerService;
import net.infumia.frame.slot.LayoutSlot;
import net.infumia.frame.typedkey.TypedKeyStorageImmutable;
import net.infumia.frame.view.config.ViewConfig;
import net.infumia.frame.view.config.option.ViewConfigOptions;
import net.infumia.frame.viewer.Viewer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ViewImpl implements View, ViewEventHandler {

    private final PipelinesView pipelines = new PipelinesViewImpl(this);
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
    public PipelinesView pipelines() {
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
    public CompletableFuture<ContextRender> simulateOpenActive(
        @NotNull final ContextRender ctx,
        @NotNull final Collection<Player> viewers
    ) {
        return this.pipelines.executeCreateViewers(viewers)
            .thenCompose(((ContextRenderRich) ctx)::simulateNavigate)
            .thenApply(__ -> ctx);
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> simulateClick(
        @NotNull final ContextRender ctx,
        @NotNull final Viewer viewer,
        @NotNull final InventoryClickEvent event
    ) {
        return this.pipelines.executeClick(ctx, viewer, event);
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> simulateClose(
        @NotNull final ContextRender ctx,
        @NotNull final Viewer viewer
    ) {
        final MetadataAccess metadata = viewer.metadata();
        final boolean transitioningFromFrame =
            metadata.remove(MetadataKeyHolder.TRANSITIONING_FROM) != null;
        final Boolean forcedClose = metadata.remove(MetadataKeyHolder.FORCED_CLOSE);
        final boolean forced = transitioningFromFrame || (forcedClose != null && forcedClose);
        return this.pipelines.executeClose(ctx, viewer, forced);
    }

    @Override
    public void handleItemPickup(
        @NotNull final ContextRender ctx,
        @NotNull final PlayerPickupItemEvent event
    ) {
        ((ViewConfigRich) ctx.config()).option(ViewConfigOptions.CANCEL_ON_PICKUP)
            .filter(l -> l)
            .ifPresent(cancel -> event.setCancelled(true));
    }

    @Override
    public void handleItemDrop(
        @NotNull final ContextRender ctx,
        @NotNull final PlayerDropItemEvent event
    ) {
        final ViewConfigRich config = (ViewConfigRich) ctx.config();
        config
            .option(ViewConfigOptions.CANCEL_ON_DROP)
            .filter(l -> l)
            .ifPresent(cancel -> event.setCancelled(true));
    }

    @Override
    public void handleInventoryDrag(
        @NotNull final ContextRender ctx,
        @NotNull final InventoryDragEvent event
    ) {
        final ViewConfigRich config = (ViewConfigRich) ctx.config();
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
        return this.pipelines.executeCreateContainer(context, config).thenCompose(container ->
            this.executeModifyContainer(context, config, container)
        );
    }

    @NotNull
    private CompletableFuture<ContextRender> executeModifyContainer(
        @NotNull final ContextBase context,
        @NotNull final ViewConfig config,
        @NotNull final ViewContainer container
    ) {
        return this.pipelines.executeModifyContainer(context, config, container).thenCompose(
            modified -> this.executeLayoutResolution(context, config, modified)
        );
    }

    @NotNull
    private CompletableFuture<ContextRender> executeLayoutResolution(
        @NotNull final ContextBase context,
        @NotNull final ViewConfig config,
        @NotNull final ViewContainer container
    ) {
        return this.pipelines.executeLayoutResolution(context, config, container).thenCompose(
            slots -> this.executeCreateRender(context, config, container, slots)
        );
    }

    @NotNull
    private CompletableFuture<ContextRender> executeCreateRender(
        @NotNull final ContextBase context,
        @NotNull final ViewConfig config,
        @NotNull final ViewContainer container,
        @NotNull final Collection<LayoutSlot> layouts
    ) {
        return this.pipelines.executeCreateRender(context, config, container, layouts).thenCompose(
            render -> ((ContextRenderRich) render).simulateFirstRender().thenApply(__ -> render)
        );
    }
}
