package net.infumia.inv.core.pipeline.service.view;

import net.infumia.inv.api.context.view.ContextOpen;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextView;
import org.jetbrains.annotations.NotNull;

public final class ServiceOpenLogging implements PipelineServiceConsumer<PipelineContextView.Open> {

    public static final PipelineServiceConsumer<PipelineContextView.Open> INSTANCE =
        new ServiceOpenLogging();

    public static final String KEY = "logging";

    @Override
    public String key() {
        return ServiceOpenLogging.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextView.Open ctx) {
        final ContextOpen context = ctx.context();
        context
            .manager()
            .logger()
            .debug("View '%s' is opening for players '%s'", context.view(), context.viewers());
    }

    private ServiceOpenLogging() {}
}
