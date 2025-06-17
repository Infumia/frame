package net.infumia.frame.service;

import io.leangen.geantyref.TypeToken;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import net.infumia.frame.Cloned;
import net.infumia.frame.Preconditions;
import net.infumia.frame.typedkey.TypedKeyStorageFactory;

/**
 * A repository for managing and executing services of a specific type.
 * It allows registering, removing, and replacing service implementations, and executing them.
 *
 * @param <Context> the context type.
 * @param <Result> the result type.
 */
public final class ServiceRepository<Context, Result>
    implements Cloned<ServiceRepository<Context, Result>> {

    private final ServiceSpigot<Context, Result> spigot;
    final TypeToken<? extends Service<Context, Result>> serviceType;
    final List<ServiceWrapper<Context, Result>> implementations;

    private ServiceRepository(
        final ServiceSpigot<Context, Result> spigot,
        final TypeToken<? extends Service<Context, Result>> serviceType,
        final List<ServiceWrapper<Context, Result>> implementations
    ) {
        Preconditions.argumentNotNull(spigot, "spigot");

        this.serviceType = Preconditions.argumentNotNull(serviceType, "serviceType");
        this.implementations = Preconditions.argumentNotNull(implementations, "implementations");
        this.spigot = spigot;
    }

    /**
     * Constructs a new {@link ServiceRepository} with a default implementation.
     *
     * @param pipeline the service pipeline.
     * @param storageFactory the storage factory.
     * @param serviceType the type token of the service.
     * @param defaultImplementation the default service implementation.
     */
    public ServiceRepository(
        final ServicePipeline pipeline,
        final TypedKeyStorageFactory storageFactory,
        final TypeToken<? extends Service<Context, Result>> serviceType,
        final Service<Context, Result> defaultImplementation
    ) {
        Preconditions.argumentNotNull(pipeline, "pipeline");
        Preconditions.argumentNotNull(storageFactory, "storageFactory");

        this.serviceType = Preconditions.argumentNotNull(serviceType, "serviceType");
        final Service<Context, Result> defImpl = Preconditions.argumentNotNull(
            defaultImplementation,
            "defaultImplementation"
        );

        this.implementations = new LinkedList<>();
        this.implementations.add(new ServiceWrapper<>(serviceType, defImpl, true, null));
        this.spigot = new ServiceSpigot<>(pipeline, this, storageFactory);
    }

    @Override
    public ServiceRepository<Context, Result> cloned() {
        return new ServiceRepository<>(
            this.spigot,
            this.serviceType,
            new LinkedList<>(this.implementations)
        );
    }

    /**
     * Applies an implementation operation to this repository.
     *
     * @param operation the operation to apply.
     */
    public void apply(final Implementation<Context, Result> operation) {
        Preconditions.argumentNotNull(operation, "operation");

        operation.handle(this);
    }

    /**
     * Executes the services in this repository directly (synchronously) with the given context.
     *
     * @param context the context to execute with.
     * @return a {@link CompletableFuture} that will be completed with the result.
     */
    public CompletableFuture<Result> completeDirect(final Context context) {
        Preconditions.argumentNotNull(context, "context");

        return this.spigot.complete(context);
    }

    /**
     * Executes the services in this repository asynchronously with the given context.
     *
     * @param context the context to execute with.
     * @return a {@link CompletableFuture} that will be completed with the result.
     */
    public CompletableFuture<Result> completeAsync(final Context context) {
        Preconditions.argumentNotNull(context, "context");

        return this.spigot.completeAsync(context);
    }

    LinkedList<ServiceWrapper<Context, Result>> queue() {
        return this.implementations.stream()
            .sorted()
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
