package net.infumia.frame.extension;

import net.infumia.frame.pipeline.PipelineConsumer;
import net.infumia.frame.pipeline.PipelineContext;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.service.Implementation;
import org.jetbrains.annotations.NotNull;

public final class PipelineExtensions {

    @NotNull
    public static <B extends PipelineContext> PipelineConsumer<B> register(
        @NotNull final PipelineConsumer<B> pipeline,
        @NotNull final PipelineServiceConsumer<B> service
    ) {
        return pipeline.apply(Implementation.register(service));
    }
}
