package net.infumia.inv.api.pipeline.executor;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.infumia.inv.api.pipeline.context.PipelineContextViewer;
import net.infumia.inv.api.service.ConsumerService;
import net.infumia.inv.api.service.Implementation;
import net.infumia.inv.api.viewer.Viewer;
import org.jetbrains.annotations.NotNull;

public interface PipelineExecutorViewer {
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
