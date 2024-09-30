package net.infumia.frame.pipeline.service.view;

import net.infumia.frame.context.view.ContextClick;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextView;
import net.infumia.frame.view.ViewHandler;
import org.jetbrains.annotations.NotNull;

public final class ServiceClickOnClick
    implements PipelineServiceConsumer<PipelineContextView.Click> {

    public static final PipelineServiceConsumer<PipelineContextView.Click> INSTANCE =
        new ServiceClickOnClick();

    public static final String KEY = "on-click";

    @Override
    public String key() {
        return ServiceClickOnClick.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextView.Click ctx) {
        final ContextClick context = ctx.context();
        final Object instance = context.view().instance();
        if (instance instanceof ViewHandler) {
            ((ViewHandler) instance).onClick(context);
        }
    }

    private ServiceClickOnClick() {}
}
