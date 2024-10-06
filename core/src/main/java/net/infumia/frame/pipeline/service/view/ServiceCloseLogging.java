package net.infumia.frame.pipeline.service.view;

import net.infumia.frame.context.view.ContextClose;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextView;
import org.jetbrains.annotations.NotNull;

public final class ServiceCloseLogging
    implements PipelineServiceConsumer<PipelineContextView.Close> {

    public static final PipelineServiceConsumer<PipelineContextView.Close> INSTANCE =
        new ServiceCloseLogging();

    public static final String KEY = "logging";

    @NotNull
    @Override
    public String key() {
        return ServiceCloseLogging.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextView.Close ctx) {
        final ContextClose context = ctx.context();
        context
            .frame()
            .logger()
            .debug(
                "View '%s' closed for viewer '%s'.",
                context.view().instance(),
                context.viewer()
            );
    }

    private ServiceCloseLogging() {}
}
