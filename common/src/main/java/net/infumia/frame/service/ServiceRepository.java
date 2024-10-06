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
    private final Service<Context, Result> defaultImplementation;
    final List<ServiceWrapper<Context, Result>> implementations;
    final TypeToken<? extends Service<Context, Result>> serviceType;

    public ServiceRepository(
        @NotNull final ServicePipeline pipeline,
        @NotNull final TypeToken<? extends Service<Context, Result>> serviceType,
        @NotNull final Service<Context, Result> defaultImplementation
    ) {
        this.pipeline = pipeline;
        this.serviceType = serviceType;
        this.defaultImplementation = defaultImplementation;
        this.implementations = new LinkedList<>();
        this.implementations.add(
                new ServiceWrapper<>(serviceType, defaultImplementation, true, null)
            );
    }

    @NotNull
    @Override
    public ServiceRepository<Context, Result> cloned() {
        return new ServiceRepository<>(this.pipeline, this.serviceType, this.defaultImplementation);
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
