package net.infumia.frame.pipeline.executor;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.pipeline.context.PipelineContextRender;
import net.infumia.frame.pipeline.context.PipelineContextView;
import net.infumia.frame.service.ConsumerService;
import net.infumia.frame.service.Implementation;
import net.infumia.frame.viewer.Viewer;
import org.jetbrains.annotations.NotNull;

public interface PipelineExecutorRender {
    @NotNull
    CompletableFuture<ConsumerService.State> executeFirstRender();

    @NotNull
    CompletableFuture<ConsumerService.State> executeStartTransition(@NotNull Collection<Viewer> viewers);

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

    void applyStartTransition(
        @NotNull Implementation<
            PipelineContextView.StartTransition,
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
