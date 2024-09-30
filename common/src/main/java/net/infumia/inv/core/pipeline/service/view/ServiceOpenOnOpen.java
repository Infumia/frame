package net.infumia.inv.core.pipeline.service.view;

import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextView;
import net.infumia.inv.api.view.ViewHandler;
import org.jetbrains.annotations.NotNull;

public final class ServiceOpenOnOpen implements PipelineServiceConsumer<PipelineContextView.Open> {

    public static final PipelineServiceConsumer<PipelineContextView.Open> INSTANCE =
        new ServiceOpenOnOpen();

    public static final String KEY = "on-open";

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
