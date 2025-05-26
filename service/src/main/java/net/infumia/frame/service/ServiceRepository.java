package net.infumia.frame.service;

import io.leangen.geantyref.TypeToken;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import net.infumia.frame.Cloned;
import net.infumia.frame.Preconditions;

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
        this.pipeline = Preconditions.argumentNotNull(pipeline, "pipeline");
        this.serviceType = Preconditions.argumentNotNull(serviceType, "serviceType");
        this.implementations = Preconditions.argumentNotNull(implementations, "implementations");
    }

    public ServiceRepository(
        final ServicePipeline pipeline,
        final TypeToken<? extends Service<Context, Result>> serviceType,
        final Service<Context, Result> defaultImplementation
    ) {
        this.pipeline = Preconditions.argumentNotNull(pipeline, "pipeline");
        this.serviceType = Preconditions.argumentNotNull(serviceType, "serviceType");
        final Service<Context, Result> defImpl = Preconditions.argumentNotNull(
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
        Preconditions.argumentNotNull(operation, "operation");

        synchronized (this.implementations) {
            operation.handle(this);
        }
    }

    public CompletableFuture<Result> completeDirect(final Context context) {
        Preconditions.argumentNotNull(context, "context");

        return new ServiceSpigot<>(this.pipeline, this, context).complete();
    }

    public CompletableFuture<Result> completeAsync(final Context context) {
        Preconditions.argumentNotNull(context, "context");

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
