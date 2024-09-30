package net.infumia.inv.core.pipeline.service.view;

import net.infumia.inv.api.context.view.ContextClick;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextView;
import net.infumia.inv.api.view.config.option.ViewConfigOptions;
import net.infumia.inv.core.config.ViewConfigRich;
import org.jetbrains.annotations.NotNull;

public final class ServiceClickCancel
    implements PipelineServiceConsumer<PipelineContextView.Click> {

    public static final PipelineServiceConsumer<PipelineContextView.Click> INSTANCE =
        new ServiceClickCancel();

    public static final String KEY = "cancel";

    @Override
    public String key() {
        return ServiceClickCancel.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextView.Click ctx) {
        final ContextClick context = ctx.context();
        ((ViewConfigRich) context.config()).option(ViewConfigOptions.CANCEL_ON_CLICK)
            .filter(cancel -> cancel)
            .ifPresent(__ -> context.cancelled(true));
    }

    private ServiceClickCancel() {}
}
