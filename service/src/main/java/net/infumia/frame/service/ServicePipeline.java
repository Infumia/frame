package net.infumia.frame.service;

import io.leangen.geantyref.TypeToken;
import java.time.Duration;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import net.infumia.frame.typedkey.TypedKeyStorageFactory;

/**
 * A pipeline for managing and executing services.
 * It allows creating {@link ServiceRepository} instances for specific service types.
 */
public final class ServicePipeline {

    final Executor executor;
    final Duration timeout;
    final ScheduledExecutorService delayer;
    final TypedKeyStorageFactory storageFactory;

    ServicePipeline(
        final Executor executor,
        final Duration timeout,
        final ScheduledExecutorService delayer,
        final TypedKeyStorageFactory storageFactory
    ) {
        this.executor = executor;
        this.timeout = timeout;
        this.delayer = delayer;
        this.storageFactory = storageFactory;
    }

    /**
     * Creates a new {@link ServiceRepository} for the given service type and default implementation.
     *
     * @param type the type token of the service.
     * @param defaultImplementation the default implementation of the service.
     * @param <Context> the context type.
     * @param <Result> the result type.
     * @return a new {@link ServiceRepository}.
     */
    public <Context, Result> ServiceRepository<Context, Result> create(
        final TypeToken<? extends Service<Context, Result>> type,
        final Service<Context, Result> defaultImplementation
    ) {
        return new ServiceRepository<>(this, this.storageFactory, type, defaultImplementation);
    }
}
