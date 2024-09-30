package net.infumia.frame.pipeline.service.state;

import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextState;
import org.jetbrains.annotations.NotNull;

public final class ServiceUpdateLogging
    implements PipelineServiceConsumer<PipelineContextState.Update> {

    public static final PipelineServiceConsumer<PipelineContextState.Update> INSTANCE =
        new ServiceUpdateLogging();

    public static final String KEY = "logging";

    @Override
    public String key() {
        return ServiceUpdateLogging.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextState.Update ctx) {
        final Object oldValue = ctx.oldValue();
        final Object newValue = ctx.value().value();
        ctx
            .manager()
            .logger()
            .debug(
                "State '%s' has been updated from '%s' to '%s'.",
                ctx.state(),
                oldValue == null ? "null" : oldValue,
                newValue == null ? "null" : newValue
            );
    }

    private ServiceUpdateLogging() {}
}
