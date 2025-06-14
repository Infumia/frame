package net.infumia.frame.pipeline;

import io.leangen.geantyref.TypeToken;
import java.util.concurrent.CompletableFuture;
import net.infumia.frame.service.*;
import org.jetbrains.annotations.NotNull;

public final class PipelineConsumerImpl<Context> implements PipelineConsumer<Context> {

    private final ServiceRepository<Context, ConsumerService.State> repository;

    private PipelineConsumerImpl(
        @NotNull final ServiceRepository<Context, ConsumerService.State> repository
    ) {
        this.repository = repository;
    }

    public PipelineConsumerImpl(
        @NotNull final TypeToken<PipelineServiceConsumer<Context>> type,
        @NotNull final PipelineServiceConsumer<Context> defaultService
    ) {
        this(ServicePipelineBuilder.newBuilder().build().create(type, defaultService));
    }

    @NotNull
    @Override
    public PipelineConsumer<Context> apply(
        @NotNull final Implementation<Context, ConsumerService.State> operation
    ) {
        this.repository.apply(operation);
        return this;
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> completeWith(@NotNull final Context context) {
        return this.repository.completeDirect(context);
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> completeWithAsync(
        @NotNull final Context context
    ) {
        return this.repository.completeAsync(context);
    }

    @NotNull
    @Override
    public PipelineConsumer<Context> cloned() {
        return new PipelineConsumerImpl<>(this.repository.cloned());
    }
}
