package net.infumia.frame.pipeline.service.frame;

import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextFrame;
import org.jetbrains.annotations.NotNull;

public final class ServiceUnregisterViewsLogging
    implements PipelineServiceConsumer<PipelineContextFrame.UnregisterViews> {

    public static final PipelineServiceConsumer<PipelineContextFrame.UnregisterViews> INSTANCE =
        new ServiceUnregisterViewsLogging();

    public static final String KEY = "logging";

    @NotNull
    @Override
    public String key() {
        return ServiceUnregisterViewsLogging.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextFrame.UnregisterViews ctx) {
        ctx.frame().logger().debug("View classes are unregistered '%s'", ctx.unregisteredViews());
    }

    private ServiceUnregisterViewsLogging() {}
}
