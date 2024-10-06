package net.infumia.frame.pipeline.service.view;

import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextView;
import net.infumia.frame.view.ViewHandler;
import org.jetbrains.annotations.NotNull;

public final class ServiceOpenOnOpen implements PipelineServiceConsumer<PipelineContextView.Open> {

    public static final PipelineServiceConsumer<PipelineContextView.Open> INSTANCE =
        new ServiceOpenOnOpen();

    public static final String KEY = "on-open";

    @NotNull
    @Override
    public String key() {
        return ServiceOpenOnOpen.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextView.Open ctx) {
        final Object instance = ctx.context().view().instance();
        if (instance instanceof ViewHandler) {
            ((ViewHandler) instance).onOpen(ctx.context());
        }
    }

    private ServiceOpenOnOpen() {}
}
