package net.infumia.frame.pipeline.service.view;

import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextView;
import org.jetbrains.annotations.NotNull;

public final class ServiceLayoutResolutionLogging
    implements PipelineServiceConsumer<PipelineContextView.LayoutResolution> {

    public static final PipelineServiceConsumer<PipelineContextView.LayoutResolution> INSTANCE =
        new ServiceLayoutResolutionLogging();

    public static final String KEY = "logging";

    @NotNull
    @Override
    public String key() {
        return ServiceLayoutResolutionLogging.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextView.LayoutResolution ctx) {
        ctx.context().frame().logger().debug("Layout '%s' resolved.", ctx.layouts());
    }

    private ServiceLayoutResolutionLogging() {}
}
