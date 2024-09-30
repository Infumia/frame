package net.infumia.inv.core.pipeline.service.view;

import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextView;
import org.jetbrains.annotations.NotNull;

public final class ServiceInitLogging implements PipelineServiceConsumer<PipelineContextView.Init> {

    public static final PipelineServiceConsumer<PipelineContextView.Init> INSTANCE =
        new ServiceInitLogging();

    public static final String KEY = "logging";

    @Override
    public String key() {
        return ServiceInitLogging.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextView.Init ctx) {
        ctx
            .view()
            .context()
            .manager()
            .logger()
            .debug("onInit ran successfully for view '%s'.", ctx.view().instance());
    }

    private ServiceInitLogging() {}
}
