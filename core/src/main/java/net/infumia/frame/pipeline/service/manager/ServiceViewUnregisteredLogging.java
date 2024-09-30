package net.infumia.frame.pipeline.service.manager;

import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextManager;
import org.jetbrains.annotations.NotNull;

public final class ServiceViewUnregisteredLogging
    implements PipelineServiceConsumer<PipelineContextManager.ViewUnregistered> {

    public static final PipelineServiceConsumer<PipelineContextManager.ViewUnregistered> INSTANCE =
        new ServiceViewUnregisteredLogging();

    public static final String KEY = "logging";

    @Override
    public String key() {
        return ServiceViewUnregisteredLogging.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextManager.ViewUnregistered ctx) {
        ctx.frame().logger().debug("View classes are unregistered '%s'", ctx.unregisteredViews());
    }

    private ServiceViewUnregisteredLogging() {}
}
