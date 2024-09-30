package net.infumia.frame.pipeline.service.render;

import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextRender;
import org.jetbrains.annotations.NotNull;

public final class ServiceFirstRenderLogging
    implements PipelineServiceConsumer<PipelineContextRender.FirstRender> {

    public static final PipelineServiceConsumer<PipelineContextRender.FirstRender> INSTANCE =
        new ServiceFirstRenderLogging();

    public static final String KEY = "logging";

    @Override
    public String key() {
        return ServiceFirstRenderLogging.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextRender.FirstRender ctx) {
        ctx
            .context()
            .frame()
            .logger()
            .debug(
                "onFirstRender ran successfully for view '%s'.",
                ctx.context().view().instance()
            );
    }

    private ServiceFirstRenderLogging() {}
}
