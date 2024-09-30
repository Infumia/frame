package net.infumia.inv.core.pipeline.service.view;

import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextView;
import org.jetbrains.annotations.NotNull;

public final class ServiceLayoutResolutionLogging
    implements PipelineServiceConsumer<PipelineContextView.LayoutResolution> {

    public static final PipelineServiceConsumer<PipelineContextView.LayoutResolution> INSTANCE =
        new ServiceLayoutResolutionLogging();

    public static final String KEY = "logging";

    @Override
    public String key() {
        return ServiceLayoutResolutionLogging.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextView.LayoutResolution ctx) {
        ctx.context().manager().logger().debug("Layout '%s' resolved.", ctx.layouts());
    }

    private ServiceLayoutResolutionLogging() {}
}
