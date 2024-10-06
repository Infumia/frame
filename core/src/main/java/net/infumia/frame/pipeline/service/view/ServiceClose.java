package net.infumia.frame.pipeline.service.view;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import net.infumia.frame.context.view.ContextClose;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextView;
import org.jetbrains.annotations.NotNull;

public final class ServiceClose implements PipelineServiceConsumer<PipelineContextView.Close> {

    public static final PipelineServiceConsumer<PipelineContextView.Close> INSTANCE =
        new ServiceClose();

    public static final String KEY = "close";

    @NotNull
    @Override
    public String key() {
        return ServiceClose.KEY;
    }

    @NotNull
    @Override
    public CompletableFuture<State> handle(@NotNull final PipelineContextView.Close ctx) {
        final ContextClose context = ctx.context();
        return context.pipelinesViewer().executeRemoved(Collections.singleton(context.viewer()));
    }

    private ServiceClose() {}
}
