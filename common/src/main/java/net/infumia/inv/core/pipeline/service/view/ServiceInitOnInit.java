package net.infumia.inv.core.pipeline.service.view;

import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextView;
import net.infumia.inv.api.view.ViewHandler;
import org.jetbrains.annotations.NotNull;

public final class ServiceInitOnInit implements PipelineServiceConsumer<PipelineContextView.Init> {

    public static final PipelineServiceConsumer<PipelineContextView.Init> INSTANCE =
        new ServiceInitOnInit();

    public static final String KEY = "on-init";

    @Override
    public String key() {
        return ServiceInitOnInit.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextView.Init ctx) {
        final Object instance = ctx.view().instance();
        if (instance instanceof ViewHandler) {
            ((ViewHandler) instance).onInit(ctx.view().context());
        }
    }

    private ServiceInitOnInit() {}
}
