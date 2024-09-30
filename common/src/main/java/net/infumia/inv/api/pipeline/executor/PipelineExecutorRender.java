package net.infumia.inv.api.pipeline.executor;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.infumia.inv.api.context.view.ContextRender;
import net.infumia.inv.api.pipeline.context.PipelineContextRender;
import net.infumia.inv.api.pipeline.context.PipelineContextView;
import net.infumia.inv.api.service.ConsumerService;
import net.infumia.inv.api.service.Implementation;
import net.infumia.inv.api.viewer.Viewer;
import org.jetbrains.annotations.NotNull;

public interface PipelineExecutorRender {
    @NotNull
    CompletableFuture<ConsumerService.State> executeFirstRender();

    @NotNull
    CompletableFuture<ConsumerService.State> executeTransition(@NotNull Collection<Viewer> viewers);

    @NotNull
    CompletableFuture<ConsumerService.State> executeOpenContainer(
        @NotNull Collection<Viewer> viewers
    );

    @NotNull
    CompletableFuture<ConsumerService.State> executeStartUpdate();

    @NotNull
    CompletableFuture<ConsumerService.State> executeResume(
        @NotNull ContextRender from,
        @NotNull Collection<Viewer> viewers
    );

    @NotNull
    CompletableFuture<ConsumerService.State> executeStopUpdate();

    @NotNull
    CompletableFuture<ConsumerService.State> executeUpdate();

    void applyFirstRender(
        @NotNull Implementation<
            PipelineContextRender.FirstRender,
            ConsumerService.State
        > implementation
    );

    void applyTransition(
        @NotNull Implementation<
            PipelineContextView.Transition,
            ConsumerService.State
        > implementation
    );

    void applyOpenContainer(
        @NotNull Implementation<
            PipelineContextRender.OpenContainer,
            ConsumerService.State
        > implementation
    );

    void applyStartUpdate(
        @NotNull Implementation<
            PipelineContextRender.StartUpdate,
            ConsumerService.State
        > implementation
    );

    void applyResume(
        @NotNull Implementation<PipelineContextRender.Resume, ConsumerService.State> implementation
    );

    void applyStopUpdate(
        @NotNull Implementation<
            PipelineContextRender.StopUpdate,
            ConsumerService.State
        > implementation
    );

    void applyUpdate(
        @NotNull Implementation<PipelineContextRender.Update, ConsumerService.State> implementation
    );
}
