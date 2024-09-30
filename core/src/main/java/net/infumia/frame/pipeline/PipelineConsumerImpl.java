package net.infumia.frame.pipeline;

import io.leangen.geantyref.TypeToken;
import java.util.concurrent.CompletableFuture;
import net.infumia.frame.service.ConsumerService;
import net.infumia.frame.service.Implementation;
import net.infumia.frame.service.ServicePipelineBuilder;
import net.infumia.frame.service.ServiceRepository;
import org.jetbrains.annotations.NotNull;

public final class PipelineConsumerImpl<B extends PipelineContext> implements PipelineConsumer<B> {

    private final ServiceRepository<B, ConsumerService.State> repository;

    public PipelineConsumerImpl(
        @NotNull final TypeToken<PipelineServiceConsumer<B>> type,
        @NotNull final PipelineServiceConsumer<B> defaultService
    ) {
        this.repository = ServicePipelineBuilder.newBuilder().build().create(type, defaultService);
    }

    @NotNull
    @Override
    public PipelineConsumer<B> apply(
        @NotNull final Implementation<B, ConsumerService.State> operation
    ) {
        this.repository.apply(operation);
        return this;
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> completeWith(@NotNull final B context) {
        return this.repository.completeWith(context);
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> completeWithAsync(@NotNull final B context) {
        return this.repository.completeWithAsync(context);
    }
}
