package net.infumia.frame.pipeline.service.frame;

import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextFrame;
import org.jetbrains.annotations.NotNull;

public final class ServiceRegisterListenersLogging
    implements PipelineServiceConsumer<PipelineContextFrame.RegisterListeners> {

    public static final PipelineServiceConsumer<PipelineContextFrame.RegisterListeners> INSTANCE =
        new ServiceRegisterListenersLogging();

    public static final String KEY = "logging";

    @NotNull
    @Override
    public String key() {
        return ServiceRegisterListenersLogging.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextFrame.RegisterListeners ctx) {
        ctx.frame().logger().debug("All the listeners are registered!");
    }

    private ServiceRegisterListenersLogging() {}
}
