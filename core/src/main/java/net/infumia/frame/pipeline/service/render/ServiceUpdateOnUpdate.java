package net.infumia.frame.pipeline.service.render;

import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextRender;
import net.infumia.frame.view.ViewHandler;
import org.jetbrains.annotations.NotNull;

public final class ServiceUpdateOnUpdate
    implements PipelineServiceConsumer<PipelineContextRender.Update> {

    public static final PipelineServiceConsumer<PipelineContextRender.Update> INSTANCE =
        new ServiceUpdateOnUpdate();

    public static final String KEY = "on-update";

    @Override
    public String key() {
        return ServiceUpdateOnUpdate.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextRender.Update ctx) {
        final ContextRender context = ctx.context();
        final Object instance = context.view().instance();
        if (instance instanceof ViewHandler) {
            ((ViewHandler) instance).onUpdate(context);
        }
    }

    private ServiceUpdateOnUpdate() {}
}
