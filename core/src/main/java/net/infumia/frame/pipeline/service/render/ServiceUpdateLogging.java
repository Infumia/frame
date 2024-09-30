package net.infumia.frame.pipeline.service.render;

import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextRender;
import org.jetbrains.annotations.NotNull;

public final class ServiceUpdateLogging
    implements PipelineServiceConsumer<PipelineContextRender.Update> {

    public static final PipelineServiceConsumer<PipelineContextRender.Update> INSTANCE =
        new ServiceUpdateLogging();

    public static final String KEY = "logging";

    @Override
    public String key() {
        return ServiceUpdateLogging.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextRender.Update ctx) {
        // TODO: portlek, More detailed message.
        ctx
            .context()
            .manager()
            .logger()
            .debug("View '%s' updated.", ctx.context().view().instance());
    }

    private ServiceUpdateLogging() {}
}
