package net.infumia.frame.pipeline.executor;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.infumia.frame.Pair;
import net.infumia.frame.context.ContextBase;
import net.infumia.frame.context.view.ContextInit;
import net.infumia.frame.context.view.ContextOpen;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.pipeline.context.PipelineContextView;
import net.infumia.frame.service.ConsumerService;
import net.infumia.frame.service.Implementation;
import net.infumia.frame.slot.LayoutSlot;
import net.infumia.frame.typedkey.TypedKeyStorageImmutable;
import net.infumia.frame.view.ViewContainer;
import net.infumia.frame.view.config.ViewConfig;
import net.infumia.frame.viewer.Viewer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

public interface PipelinesView {
    @NotNull
    CompletableFuture<ConsumerService.State> executeInit(@NotNull ContextInit context);

    @NotNull
    CompletableFuture<Collection<Viewer>> executeCreateViewers(@NotNull Collection<Player> viewers);

    @NotNull
    CompletableFuture<ContextBase> executeCreateContext(
        @NotNull Collection<Viewer> viewers,
        @NotNull TypedKeyStorageImmutable initialData
    );

    @NotNull
    CompletableFuture<Pair<ConsumerService.State, ContextOpen>> executeOpen(
        @NotNull ContextBase context
    );

    @NotNull
    CompletableFuture<ConsumerService.State> executeProcessConfigModifiers(
        @NotNull ContextOpen context
    );

    @NotNull
    CompletableFuture<ViewContainer> executeCreateContainer(
        @NotNull ContextBase context,
        @NotNull ViewConfig config
    );

    @NotNull
    CompletableFuture<ViewContainer> executeModifyContainer(
        @NotNull ContextBase context,
        @NotNull ViewConfig config,
        @NotNull ViewContainer container
    );

    @NotNull
    CompletableFuture<Collection<LayoutSlot>> executeLayoutResolution(
        @NotNull ContextBase context,
        @NotNull ViewConfig config,
        @NotNull ViewContainer container
    );

    @NotNull
    CompletableFuture<ContextRender> executeCreateRender(
        @NotNull ContextBase context,
        @NotNull ViewConfig config,
        @NotNull ViewContainer container,
        @NotNull Collection<LayoutSlot> layouts
    );

    @NotNull
    CompletableFuture<ConsumerService.State> executeClick(
        @NotNull ContextRender ctx,
        @NotNull Viewer clicker,
        @NotNull InventoryClickEvent event
    );

    @NotNull
    CompletableFuture<ConsumerService.State> executeClose(
        @NotNull ContextRender ctx,
        @NotNull Viewer clicker,
        boolean forced
    );

    void applyInit(
        @NotNull Implementation<PipelineContextView.Init, ConsumerService.State> implementation
    );

    void applyCreateViewers(
        @NotNull Implementation<
            PipelineContextView.CreateViewers,
            Collection<Viewer>
        > implementation
    );

    void applyCreateContext(
        @NotNull Implementation<PipelineContextView.CreateContext, ContextBase> implementation
    );

    void applyOpen(
        @NotNull Implementation<PipelineContextView.Open, ConsumerService.State> implementation
    );

    void applyProcessConfigModifiers(
        @NotNull Implementation<
            PipelineContextView.ProcessConfigModifier,
            ConsumerService.State
        > implementation
    );

    void applyCreateContainer(
        @NotNull Implementation<PipelineContextView.CreateContainer, ViewContainer> implementation
    );

    void applyModifyContainer(
        @NotNull Implementation<
            PipelineContextView.ModifyContainer,
            ConsumerService.State
        > implementation
    );

    void applyLayoutResolution(
        @NotNull Implementation<
            PipelineContextView.LayoutResolution,
            ConsumerService.State
        > implementation
    );

    void applyCreateRender(
        @NotNull Implementation<PipelineContextView.CreateRender, ContextRender> implementation
    );

    void applyClick(
        @NotNull Implementation<PipelineContextView.Click, ConsumerService.State> implementation
    );

    void applyClose(
        @NotNull Implementation<PipelineContextView.Close, ConsumerService.State> implementation
    );
}
