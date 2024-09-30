package net.infumia.frame.pipeline.service.frame;

import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextFrame;
import org.jetbrains.annotations.NotNull;

public final class ServiceViewUnregisteredLogging
    implements PipelineServiceConsumer<PipelineContextFrame.ViewUnregistered> {

    public static final PipelineServiceConsumer<PipelineContextFrame.ViewUnregistered> INSTANCE =
        new ServiceViewUnregisteredLogging();

    public static final String KEY = "logging";

    @Override
    public String key() {
        return ServiceViewUnregisteredLogging.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextFrame.ViewUnregistered ctx) {
        ctx.frame().logger().debug("View classes are unregistered '%s'", ctx.unregisteredViews());
    }

    private ServiceViewUnregisteredLogging() {}
}