package net.infumia.frame.pipeline;

import io.leangen.geantyref.TypeToken;
import java.util.concurrent.CompletableFuture;
import net.infumia.frame.service.Implementation;
import net.infumia.frame.service.ServicePipelineBuilder;
import net.infumia.frame.service.ServiceRepository;
import org.jetbrains.annotations.NotNull;

public class PipelineImpl<Context, Result> implements Pipeline<Context, Result> {

    private final ServiceRepository<Context, Result> repository;

    private PipelineImpl(@NotNull final ServiceRepository<Context, Result> repository) {
        this.repository = repository;
    }

    public PipelineImpl(
        @NotNull final TypeToken<PipelineService<Context, Result>> type,
        @NotNull final PipelineService<Context, Result> defaultService
    ) {
        this(ServicePipelineBuilder.newBuilder().build().create(type, defaultService));
    }

    @NotNull
    @Override
    public Pipeline<Context, Result> apply(
        @NotNull final Implementation<Context, Result> operation
    ) {
        this.repository.apply(operation);
        return this;
    }

    @NotNull
    @Override
    public CompletableFuture<Result> completeWith(@NotNull final Context context) {
        return this.repository.completeWith(context);
    }

    @NotNull
    @Override
    public CompletableFuture<Result> completeWithAsync(@NotNull final Context context) {
        return this.repository.completeWithAsync(context);
    }

    @NotNull
    @Override
    public Pipeline<Context, Result> cloned() {
        return new PipelineImpl<>(this.repository.cloned());
    }
}
