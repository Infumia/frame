package net.infumia.frame.pipeline.service.render;

import net.infumia.frame.context.view.ContextResume;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextRender;
import net.infumia.frame.view.ViewHandler;
import org.jetbrains.annotations.NotNull;

public final class ServiceResumeOnResume
    implements PipelineServiceConsumer<PipelineContextRender.Resume> {

    public static final PipelineServiceConsumer<PipelineContextRender.Resume> INSTANCE =
        new ServiceResumeOnResume();

    public static final String KEY = "on-resume";

    @NotNull
    @Override
    public String key() {
        return ServiceResumeOnResume.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextRender.Resume ctx) {
        final ContextResume context = ctx.context();
        final Object instance = context.view().instance();
        if (instance instanceof ViewHandler) {
            ((ViewHandler) instance).onResume(context);
        }
    }

    private ServiceResumeOnResume() {}
}
