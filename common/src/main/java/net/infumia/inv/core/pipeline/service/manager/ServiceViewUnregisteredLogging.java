package net.infumia.inv.core.pipeline.service.manager;

import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextManager;
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
        ctx.manager().logger().debug("View classes are unregistered '%s'", ctx.unregisteredViews());
    }

    private ServiceViewUnregisteredLogging() {}
}
