package net.infumia.inv.api.pipeline.executor;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import net.infumia.inv.api.context.ContextBase;
import net.infumia.inv.api.context.view.ContextInit;
import net.infumia.inv.api.context.view.ContextOpen;
import net.infumia.inv.api.context.view.ContextRender;
import net.infumia.inv.api.pipeline.context.PipelineContextView;
import net.infumia.inv.api.service.ConsumerService;
import net.infumia.inv.api.service.Implementation;
import net.infumia.inv.api.slot.LayoutSlot;
import net.infumia.inv.api.typedkey.TypedKeyStorageImmutable;
import net.infumia.inv.api.util.Pair;
import net.infumia.inv.api.view.ViewContainer;
import net.infumia.inv.api.view.config.ViewConfig;
import net.infumia.inv.api.viewer.ContextualViewer;
import net.infumia.inv.api.viewer.Viewer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

public interface PipelineExecutorView {
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
    CompletableFuture<
        Pair<ConsumerService.State, PipelineContextView.ModifyContainer>
    > executeModifyContainer(
        @NotNull ContextBase context,
        @NotNull ViewConfig config,
        @NotNull ViewContainer container
    );

    @NotNull
    CompletableFuture<
        Pair<ConsumerService.State, Map<Character, LayoutSlot>>
    > executeLayoutResolution(
        @NotNull ContextBase context,
        @NotNull ViewConfig config,
        @NotNull ViewContainer container
    );

    @NotNull
    CompletableFuture<ContextRender> executeCreateRender(
        @NotNull ContextBase context,
        @NotNull ViewConfig config,
        @NotNull ViewContainer container,
        @NotNull Map<Character, LayoutSlot> layouts
    );

    @NotNull
    CompletableFuture<ConsumerService.State> executeClick(
        @NotNull ContextualViewer clicker,
        @NotNull InventoryClickEvent event
    );

    @NotNull
    CompletableFuture<ConsumerService.State> executeClose(
        @NotNull ContextualViewer viewer,
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
