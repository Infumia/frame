package net.infumia.inv.core.pipeline.service.render;

import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextRender;
import org.jetbrains.annotations.NotNull;

public final class ServiceStopUpdateLogging
    implements PipelineServiceConsumer<PipelineContextRender.StopUpdate> {

    public static final PipelineServiceConsumer<PipelineContextRender.StopUpdate> INSTANCE =
        new ServiceStopUpdateLogging();

    public static final String KEY = "logging";

    @Override
    public String key() {
        return ServiceStopUpdateLogging.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextRender.StopUpdate ctx) {
        // TODO: portlek, More detailed message.
        ctx
            .context()
            .manager()
            .logger()
            .debug("Update task stopped for view '%s'.", ctx.context().view().instance());
    }

    private ServiceStopUpdateLogging() {}
}
