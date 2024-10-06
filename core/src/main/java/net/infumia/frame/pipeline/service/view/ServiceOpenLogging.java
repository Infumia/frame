package net.infumia.frame.pipeline.service.view;

import net.infumia.frame.context.view.ContextOpen;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextView;
import org.jetbrains.annotations.NotNull;

public final class ServiceOpenLogging implements PipelineServiceConsumer<PipelineContextView.Open> {

    public static final PipelineServiceConsumer<PipelineContextView.Open> INSTANCE =
        new ServiceOpenLogging();

    public static final String KEY = "logging";

    @NotNull
    @Override
    public String key() {
        return ServiceOpenLogging.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextView.Open ctx) {
        final ContextOpen context = ctx.context();
        context
            .frame()
            .logger()
            .debug("View '%s' is opening for players '%s'", context.view(), context.viewers());
    }

    private ServiceOpenLogging() {}
}
