package net.infumia.frame.pipeline.service.view;

import net.infumia.frame.config.ViewConfigRich;
import net.infumia.frame.context.view.ContextClick;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextView;
import net.infumia.frame.view.config.option.ViewConfigOptions;
import org.jetbrains.annotations.NotNull;

public final class ServiceClickCancel
    implements PipelineServiceConsumer<PipelineContextView.Click> {

    public static final PipelineServiceConsumer<PipelineContextView.Click> INSTANCE =
        new ServiceClickCancel();

    public static final String KEY = "cancel";

    @NotNull
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
