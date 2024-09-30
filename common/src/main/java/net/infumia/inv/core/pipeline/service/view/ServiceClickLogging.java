package net.infumia.inv.core.pipeline.service.view;

import net.infumia.inv.api.context.view.ContextClick;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextView;
import org.jetbrains.annotations.NotNull;

public final class ServiceClickLogging
    implements PipelineServiceConsumer<PipelineContextView.Click> {

    public static final PipelineServiceConsumer<PipelineContextView.Click> INSTANCE =
        new ServiceClickLogging();

    public static final String KEY = "logging";

    @Override
    public String key() {
        return ServiceClickLogging.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextView.Click ctx) {
        // TODO: portlek, More detailed message.
        final ContextClick context = ctx.context();
        context
            .manager()
            .logger()
            .debug(
                "Player '%s' clicked on view '%s'.",
                context.clicker(),
                context.view().instance()
            );
    }

    private ServiceClickLogging() {}
}
