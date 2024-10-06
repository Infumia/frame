package net.infumia.frame.pipeline.service.state;

import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextState;
import org.jetbrains.annotations.NotNull;

public final class ServiceAccessLogging
    implements PipelineServiceConsumer<PipelineContextState.Access> {

    public static final PipelineServiceConsumer<PipelineContextState.Access> INSTANCE =
        new ServiceAccessLogging();

    public static final String KEY = "logging";

    @NotNull
    @Override
    public String key() {
        return ServiceAccessLogging.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextState.Access ctx) {
        final Object value = ctx.value().value();
        ctx
            .frame()
            .logger()
            .debug(
                "State '%s' has been accessed for value '%s'.",
                ctx.state(),
                value == null ? "null" : value
            );
    }

    private ServiceAccessLogging() {}
}
