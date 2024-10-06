package net.infumia.frame.service;

import io.leangen.geantyref.TypeToken;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import net.infumia.frame.util.Cloned;
import org.jetbrains.annotations.NotNull;

public final class ServiceRepository<Context, Result>
    implements Cloned<ServiceRepository<Context, Result>> {

    private final ServicePipeline pipeline;
    final TypeToken<? extends Service<Context, Result>> serviceType;
    final List<ServiceWrapper<Context, Result>> implementations;

    private ServiceRepository(
        @NotNull final ServicePipeline pipeline,
        @NotNull final TypeToken<? extends Service<Context, Result>> serviceType,
        @NotNull final List<ServiceWrapper<Context, Result>> implementations
    ) {
        this.pipeline = pipeline;
        this.serviceType = serviceType;
        this.implementations = implementations;
    }

    public ServiceRepository(
        @NotNull final ServicePipeline pipeline,
        @NotNull final TypeToken<? extends Service<Context, Result>> serviceType,
        @NotNull final Service<Context, Result> defaultImplementation
    ) {
        this.pipeline = pipeline;
        this.serviceType = serviceType;
        this.implementations = new LinkedList<>();
        this.implementations.add(
                new ServiceWrapper<>(serviceType, defaultImplementation, true, null)
            );
    }

    @NotNull
    @Override
    public ServiceRepository<Context, Result> cloned() {
        return new ServiceRepository<>(
            this.pipeline,
            this.serviceType,
            new LinkedList<>(this.implementations)
        );
    }

    public void apply(@NotNull final Implementation<Context, Result> operation) {
        synchronized (this.implementations) {
            operation.handle(this);
        }
    }

    @NotNull
    public CompletableFuture<Result> completeWith(@NotNull final Context context) {
        return new ServiceSpigot<>(this.pipeline, this, context).complete();
    }

    @NotNull
    public CompletableFuture<Result> completeWithAsync(@NotNull final Context context) {
        return new ServiceSpigot<>(this.pipeline, this, context).completeAsync();
    }

    @NotNull
    LinkedList<ServiceWrapper<Context, Result>> queue() {
        synchronized (this.implementations) {
            return this.implementations.stream()
                .sorted()
                .collect(Collectors.toCollection(LinkedList::new));
        }
    }
}
