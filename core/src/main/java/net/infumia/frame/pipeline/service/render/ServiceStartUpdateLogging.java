package net.infumia.frame.pipeline.service.render;

import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextRender;
import org.jetbrains.annotations.NotNull;

public final class ServiceStartUpdateLogging
    implements PipelineServiceConsumer<PipelineContextRender.StartUpdate> {

    public static final PipelineServiceConsumer<PipelineContextRender.StartUpdate> INSTANCE =
        new ServiceStartUpdateLogging();

    public static final String KEY = "logging";

    @Override
    public String key() {
        return ServiceStartUpdateLogging.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextRender.StartUpdate ctx) {
        // TODO: portlek, More detailed message.
        ctx
            .context()
            .frame()
            .logger()
            .debug("Update task started for view '%s'.", ctx.context().view().instance());
    }

    private ServiceStartUpdateLogging() {}
}
