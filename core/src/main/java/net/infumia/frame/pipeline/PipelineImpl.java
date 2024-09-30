package net.infumia.frame.pipeline;

import io.leangen.geantyref.TypeToken;
import java.util.concurrent.CompletableFuture;
import net.infumia.frame.service.Implementation;
import net.infumia.frame.service.ServicePipelineBuilder;
import net.infumia.frame.service.ServiceRepository;
import org.jetbrains.annotations.NotNull;

public class PipelineImpl<B extends PipelineContext, R> implements Pipeline<B, R> {

    private final ServiceRepository<B, R> repository;

    public PipelineImpl(
        @NotNull final TypeToken<PipelineService<B, R>> type,
        @NotNull final PipelineService<B, R> defaultService
    ) {
        this.repository = ServicePipelineBuilder.newBuilder().build().create(type, defaultService);
    }

    @NotNull
    @Override
    public Pipeline<B, R> apply(@NotNull final Implementation<B, R> operation) {
        this.repository.apply(operation);
        return this;
    }

    @NotNull
    @Override
    public CompletableFuture<R> completeWith(@NotNull final B context) {
        return this.repository.completeWith(context);
    }

    @NotNull
    @Override
    public CompletableFuture<R> completeWithAsync(@NotNull final B context) {
        return this.repository.completeWithAsync(context);
    }
}
