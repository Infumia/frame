package net.infumia.inv.core.pipeline.service.render;

import net.infumia.inv.api.context.view.ContextResume;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextRender;
import org.jetbrains.annotations.NotNull;

public final class ServiceResumeLogging
    implements PipelineServiceConsumer<PipelineContextRender.Resume> {

    public static final PipelineServiceConsumer<PipelineContextRender.Resume> INSTANCE =
        new ServiceResumeLogging();

    public static final String KEY = "logging";

    @Override
    public String key() {
        return ServiceResumeLogging.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextRender.Resume ctx) {
        // TODO: portlek, More detailed message.
        final ContextResume context = ctx.context();
        context
            .manager()
            .logger()
            .debug(
                "Viewers '%s' resumed from view '%s'.",
                context.resumedViewers(),
                context.from().view().instance()
            );
    }

    private ServiceResumeLogging() {}
}
