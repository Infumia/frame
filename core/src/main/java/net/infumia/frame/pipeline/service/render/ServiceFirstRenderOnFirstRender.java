package net.infumia.frame.pipeline.service.render;

import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextRender;
import net.infumia.frame.view.ViewHandler;
import org.jetbrains.annotations.NotNull;

public final class ServiceFirstRenderOnFirstRender
    implements PipelineServiceConsumer<PipelineContextRender.FirstRender> {

    public static final PipelineServiceConsumer<PipelineContextRender.FirstRender> INSTANCE =
        new ServiceFirstRenderOnFirstRender();

    public static final String KEY = "on-first-render";

    @Override
    public String key() {
        return ServiceFirstRenderOnFirstRender.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextRender.FirstRender ctx) {
        final Object instance = ctx.context().view().instance();
        if (instance instanceof ViewHandler) {
            ((ViewHandler) instance).onFirstRender(ctx.context());
        }
    }

    private ServiceFirstRenderOnFirstRender() {}
}
