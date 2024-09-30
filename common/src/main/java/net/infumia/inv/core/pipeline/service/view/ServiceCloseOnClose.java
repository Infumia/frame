package net.infumia.inv.core.pipeline.service.view;

import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextView;
import net.infumia.inv.api.view.ViewHandler;
import org.jetbrains.annotations.NotNull;

public final class ServiceCloseOnClose
    implements PipelineServiceConsumer<PipelineContextView.Close> {

    public static final PipelineServiceConsumer<PipelineContextView.Close> INSTANCE =
        new ServiceCloseOnClose();

    public static final String KEY = "on-close";

    @Override
    public String key() {
        return ServiceCloseOnClose.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextView.Close ctx) {
        final Object instance = ctx.context().view().instance();
        if (instance instanceof ViewHandler) {
            ((ViewHandler) instance).onClose(ctx.context());
        }
    }

    private ServiceCloseOnClose() {}
}
