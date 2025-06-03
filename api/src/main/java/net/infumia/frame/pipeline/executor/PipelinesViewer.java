package net.infumia.frame.pipeline.executor;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.infumia.frame.pipeline.context.PipelineContextViewer;
import net.infumia.frame.service.ConsumerService;
import net.infumia.frame.service.Implementation;
import net.infumia.frame.viewer.Viewer;
import org.jetbrains.annotations.NotNull;

public interface PipelinesViewer {
    @NotNull
    CompletableFuture<ConsumerService.State> executeAdded(@NotNull Collection<Viewer> viewers);

    @NotNull
    CompletableFuture<ConsumerService.State> executeRemoved(@NotNull Collection<Viewer> viewers);

    void applyAdded(
        @NotNull Implementation<PipelineContextViewer.Added, ConsumerService.State> implementation
    );

    void applyRemoved(
        @NotNull Implementation<PipelineContextViewer.Removed, ConsumerService.State> implementation
    );
}
