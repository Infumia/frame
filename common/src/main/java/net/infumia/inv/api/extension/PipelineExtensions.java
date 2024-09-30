package net.infumia.inv.api.extension;

import net.infumia.inv.api.pipeline.PipelineConsumer;
import net.infumia.inv.api.pipeline.PipelineContext;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.service.Implementation;
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
