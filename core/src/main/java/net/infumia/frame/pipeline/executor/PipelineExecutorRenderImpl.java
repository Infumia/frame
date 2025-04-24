package net.infumia.frame.pipeline.executor;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.context.view.ContextResumeImpl;
import net.infumia.frame.pipeline.context.PipelineContextRender;
import net.infumia.frame.pipeline.context.PipelineContextRenders;
import net.infumia.frame.pipeline.context.PipelineContextView;
import net.infumia.frame.pipeline.context.PipelineContextViews;
import net.infumia.frame.pipeline.holder.PipelineHolderRender;
import net.infumia.frame.service.ConsumerService;
import net.infumia.frame.service.Implementation;
import net.infumia.frame.viewer.Viewer;
import org.jetbrains.annotations.NotNull;

public final class PipelineExecutorRenderImpl implements PipelineExecutorRender {

    private final PipelineHolderRender pipelines = PipelineHolderRender.BASE.cloned();
    private final ContextRender context;

    public PipelineExecutorRenderImpl(@NotNull final ContextRender context) {
        this.context = context;
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> executeFirstRender() {
        return this.pipelines.firstRender()
            .completeWith(new PipelineContextRenders.FirstRender(this.context));
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> executeStartTransition(
        @NotNull final Collection<Viewer> viewers
    ) {
        return this.pipelines.startTransition()
            .completeWith(new PipelineContextViews.StartTransition(this.context, viewers));
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> executeOpenContainer(
        @NotNull final Collection<Viewer> viewers
    ) {
        return this.pipelines.openContainer()
            .completeWith(new PipelineContextRenders.OpenContainer(this.context, viewers));
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> executeStartUpdate() {
        return this.pipelines.startUpdate()
            .completeWith(new PipelineContextRenders.StartUpdate(this.context));
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> executeResume(
        @NotNull final ContextRender from,
        @NotNull final Collection<Viewer> viewers
    ) {
        return this.pipelines.resume()
            .completeWith(
                new PipelineContextRenders.Resume(
                    new ContextResumeImpl(this.context, from, viewers)
                )
            );
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> executeStopUpdate() {
        return this.pipelines.stopUpdate()
            .completeWith(new PipelineContextRenders.StopUpdate(this.context));
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> executeUpdate() {
        return this.pipelines.update()
            .completeWith(new PipelineContextRenders.Update(this.context));
    }

    @Override
    public void applyFirstRender(
        @NotNull final Implementation<
            PipelineContextRender.FirstRender,
            ConsumerService.State
        > implementation
    ) {
        this.pipelines.firstRender().apply(implementation);
    }

    @Override
    public void applyStartTransition(
        @NotNull final Implementation<
            PipelineContextView.StartTransition,
            ConsumerService.State
        > implementation
    ) {
        this.pipelines.startTransition().apply(implementation);
    }

    @Override
    public void applyOpenContainer(
        @NotNull final Implementation<
            PipelineContextRender.OpenContainer,
            ConsumerService.State
        > implementation
    ) {
        this.pipelines.openContainer().apply(implementation);
    }

    @Override
    public void applyStartUpdate(
        @NotNull final Implementation<
            PipelineContextRender.StartUpdate,
            ConsumerService.State
        > implementation
    ) {
        this.pipelines.startUpdate().apply(implementation);
    }

    @Override
    public void applyResume(
        @NotNull final Implementation<
            PipelineContextRender.Resume,
            ConsumerService.State
        > implementation
    ) {
        this.pipelines.resume().apply(implementation);
    }

    @Override
    public void applyStopUpdate(
        @NotNull final Implementation<
            PipelineContextRender.StopUpdate,
            ConsumerService.State
        > implementation
    ) {
        this.pipelines.stopUpdate().apply(implementation);
    }

    @Override
    public void applyUpdate(
        @NotNull final Implementation<
            PipelineContextRender.Update,
            ConsumerService.State
        > implementation
    ) {
        this.pipelines.update().apply(implementation);
    }
}
