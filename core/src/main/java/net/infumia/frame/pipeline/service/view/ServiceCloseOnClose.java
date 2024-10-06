package net.infumia.frame.pipeline.service.view;

import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextView;
import net.infumia.frame.view.ViewHandler;
import org.jetbrains.annotations.NotNull;

public final class ServiceCloseOnClose
    implements PipelineServiceConsumer<PipelineContextView.Close> {

    public static final PipelineServiceConsumer<PipelineContextView.Close> INSTANCE =
        new ServiceCloseOnClose();

    public static final String KEY = "on-close";

    @NotNull
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
