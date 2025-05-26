package net.infumia.frame.service;

import io.leangen.geantyref.TypeToken;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import net.infumia.frame.Cloned;

public final class ServiceRepository<Context, Result>
    implements Cloned<ServiceRepository<Context, Result>> {

    private final ServicePipeline pipeline;
    final TypeToken<? extends Service<Context, Result>> serviceType;
    final List<ServiceWrapper<Context, Result>> implementations;

    private ServiceRepository(
        final ServicePipeline pipeline,
        final TypeToken<? extends Service<Context, Result>> serviceType,
        final List<ServiceWrapper<Context, Result>> implementations
    ) {
        this.pipeline = Objects.requireNonNull(pipeline, "pipeline");
        this.serviceType = Objects.requireNonNull(serviceType, "serviceType");
        this.implementations = Objects.requireNonNull(implementations, "implementations");
    }

    public ServiceRepository(
        final ServicePipeline pipeline,
        final TypeToken<? extends Service<Context, Result>> serviceType,
        final Service<Context, Result> defaultImplementation
    ) {
        this.pipeline = Objects.requireNonNull(pipeline, "pipeline");
        this.serviceType = Objects.requireNonNull(serviceType, "serviceType");
        final Service<Context, Result> defImpl = Objects.requireNonNull(
            defaultImplementation,
            "defaultImplementation"
        );

        this.implementations = new LinkedList<>();
        this.implementations.add(new ServiceWrapper<>(serviceType, defImpl, true, null));
    }

    @Override
    public ServiceRepository<Context, Result> cloned() {
        return new ServiceRepository<>(
            this.pipeline,
            this.serviceType,
            new LinkedList<>(this.implementations)
        );
    }

    public void apply(final Implementation<Context, Result> operation) {
        Objects.requireNonNull(operation, "operation");

        synchronized (this.implementations) {
            operation.handle(this);
        }
    }

    public CompletableFuture<Result> completeDirect(final Context context) {
        Objects.requireNonNull(context, "context");

        return new ServiceSpigot<>(this.pipeline, this, context).complete();
    }

    public CompletableFuture<Result> completeAsync(final Context context) {
        Objects.requireNonNull(context, "context");

        return new ServiceSpigot<>(this.pipeline, this, context).completeAsync();
    }

    LinkedList<ServiceWrapper<Context, Result>> queue() {
        synchronized (this.implementations) {
            return this.implementations.stream()
                .sorted()
                .collect(Collectors.toCollection(LinkedList::new));
        }
    }
}
