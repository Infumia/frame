package net.infumia.inv.core.pipeline.service.view;

import net.infumia.inv.api.context.view.ContextClick;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextView;
import net.infumia.inv.api.view.ViewHandler;
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
