package net.infumia.frame.pipeline.service.frame;

import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextFrame;
import org.jetbrains.annotations.NotNull;

public final class ServiceListenerRegisteredLogging
    implements PipelineServiceConsumer<PipelineContextFrame.ListenerRegistered> {

    public static final PipelineServiceConsumer<PipelineContextFrame.ListenerRegistered> INSTANCE =
        new ServiceListenerRegisteredLogging();

    public static final String KEY = "logging";

    @Override
    public String key() {
        return ServiceListenerRegisteredLogging.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextFrame.ListenerRegistered ctx) {
        ctx.frame().logger().debug("All the listeners are registered!");
    }

    private ServiceListenerRegisteredLogging() {}
}
