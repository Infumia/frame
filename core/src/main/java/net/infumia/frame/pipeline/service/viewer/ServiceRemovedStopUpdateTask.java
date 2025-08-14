package net.infumia.frame.pipeline.service.viewer;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextViewer;
import net.infumia.frame.service.ConsumerService;
import org.jetbrains.annotations.NotNull;

public final class ServiceRemovedStopUpdateTask
    implements PipelineServiceConsumer<PipelineContextViewer.Removed> {

    public static final PipelineServiceConsumer<PipelineContextViewer.Removed> INSTANCE =
        new ServiceRemovedStopUpdateTask();

    public static final String KEY = "stop-update-task";

    @NotNull
    @Override
    public String key() {
        return ServiceRemovedStopUpdateTask.KEY;
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> handle(
        @NotNull final PipelineContextViewer.Removed ctx
    ) {
        final ContextRender context = ctx.context();
        if (context.viewers().isEmpty()) {
            return context.pipelines().executeStopUpdate();
        }
        return CompletableFuture.completedFuture(ConsumerService.State.CONTINUE);
    }

    private ServiceRemovedStopUpdateTask() {}
}
