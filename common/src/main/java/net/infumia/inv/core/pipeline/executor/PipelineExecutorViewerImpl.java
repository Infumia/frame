package net.infumia.inv.core.pipeline.executor;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.infumia.inv.api.pipeline.context.PipelineContextViewer;
import net.infumia.inv.api.pipeline.executor.PipelineExecutorViewer;
import net.infumia.inv.api.service.ConsumerService;
import net.infumia.inv.api.service.Implementation;
import net.infumia.inv.api.viewer.Viewer;
import net.infumia.inv.core.context.view.ContextRenderRich;
import net.infumia.inv.core.pipeline.context.PipelineContextViewers;
import net.infumia.inv.core.pipeline.holder.PipelineHolderViewer;
import org.jetbrains.annotations.NotNull;

public final class PipelineExecutorViewerImpl implements PipelineExecutorViewer {

    private final PipelineHolderViewer pipelines = PipelineHolderViewer.BASE.createNew();
    private final ContextRenderRich context;

    public PipelineExecutorViewerImpl(@NotNull final ContextRenderRich context) {
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
