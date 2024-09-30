package net.infumia.frame.pipeline.service.manager;

import net.infumia.frame.FrameRich;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextManager;
import org.jetbrains.annotations.NotNull;

public final class ServiceListenerRegistered
    implements PipelineServiceConsumer<PipelineContextManager.ListenerRegistered> {

    public static final PipelineServiceConsumer<
        PipelineContextManager.ListenerRegistered
    > INSTANCE = new ServiceListenerRegistered();

    public static final String KEY = "register";

    @Override
    public String key() {
        return ServiceListenerRegistered.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextManager.ListenerRegistered ctx) {
        ((FrameRich) ctx.manager()).listener().register();
    }

    private ServiceListenerRegistered() {}
}
