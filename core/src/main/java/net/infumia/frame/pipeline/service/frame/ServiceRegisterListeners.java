package net.infumia.frame.pipeline.service.frame;

import net.infumia.frame.FrameRich;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextFrame;
import org.jetbrains.annotations.NotNull;

public final class ServiceRegisterListeners
    implements PipelineServiceConsumer<PipelineContextFrame.RegisterListeners> {

    public static final PipelineServiceConsumer<PipelineContextFrame.RegisterListeners> INSTANCE =
        new ServiceRegisterListeners();

    public static final String KEY = "register";

    @NotNull
    @Override
    public String key() {
        return ServiceRegisterListeners.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextFrame.RegisterListeners ctx) {
        ((FrameRich) ctx.frame()).listener().register();
    }

    private ServiceRegisterListeners() {}
}
