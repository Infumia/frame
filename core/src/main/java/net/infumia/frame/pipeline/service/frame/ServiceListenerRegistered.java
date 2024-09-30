package net.infumia.frame.pipeline.service.frame;

import net.infumia.frame.FrameRich;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextFrame;
import org.jetbrains.annotations.NotNull;

public final class ServiceListenerRegistered
    implements PipelineServiceConsumer<PipelineContextFrame.ListenerRegistered> {

    public static final PipelineServiceConsumer<
        PipelineContextFrame.ListenerRegistered
    > INSTANCE = new ServiceListenerRegistered();

    public static final String KEY = "register";

    @Override
    public String key() {
        return ServiceListenerRegistered.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextFrame.ListenerRegistered ctx) {
        ((FrameRich) ctx.frame()).listener().register();
    }

    private ServiceListenerRegistered() {}
}
