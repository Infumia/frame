package net.infumia.inv.core.pipeline.service.manager;

import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextManager;
import org.jetbrains.annotations.NotNull;

public final class ServiceListenerRegisteredLogging
    implements PipelineServiceConsumer<PipelineContextManager.ListenerRegistered> {

    public static final PipelineServiceConsumer<
        PipelineContextManager.ListenerRegistered
    > INSTANCE = new ServiceListenerRegisteredLogging();

    public static final String KEY = "logging";

    @Override
    public String key() {
        return ServiceListenerRegisteredLogging.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextManager.ListenerRegistered ctx) {
        ctx.manager().logger().debug("All the listeners are registered!");
    }

    private ServiceListenerRegisteredLogging() {}
}
