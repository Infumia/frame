package net.infumia.frame.pipeline.executor;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.pipeline.context.PipelineContextViewer;
import net.infumia.frame.pipeline.context.PipelineContextViewers;
import net.infumia.frame.pipeline.holder.PipelineHolderViewer;
import net.infumia.frame.service.ConsumerService;
import net.infumia.frame.service.Implementation;
import net.infumia.frame.viewer.Viewer;
import org.jetbrains.annotations.NotNull;

public final class PipelineExecutorViewerImpl implements PipelineExecutorViewer {

    private final PipelineHolderViewer pipelines = PipelineHolderViewer.BASE.cloned();
    private final ContextRender context;

    public PipelineExecutorViewerImpl(@NotNull final ContextRender context) {
        this.context = context;
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> executeAdded(
        @NotNull final Collection<Viewer> viewers
    ) {
        return this.pipelines.added()
            .completeWith(new PipelineContextViewers.Added(this.context, viewers));
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> executeRemoved(
        @NotNull final Collection<Viewer> viewers
    ) {
        return this.pipelines.removed()
            .completeWith(new PipelineContextViewers.Removed(this.context, viewers));
    }

    @Override
    public void applyAdded(
        @NotNull final Implementation<
            PipelineContextViewer.Added,
            ConsumerService.State
        > implementation
    ) {
        this.pipelines.added().apply(implementation);
    }

    @Override
    public void applyRemoved(
        @NotNull final Implementation<
            PipelineContextViewer.Removed,
            ConsumerService.State
        > implementation
    ) {
        this.pipelines.removed().apply(implementation);
    }
}
