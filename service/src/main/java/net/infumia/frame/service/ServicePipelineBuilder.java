package net.infumia.frame.service;

import java.time.Duration;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import net.infumia.frame.Preconditions;
import net.infumia.frame.typedkey.TypedKeyStorageFactory;

/**
 * A builder for creating {@link ServicePipeline} instances.
 */
public final class ServicePipelineBuilder {

    private Executor executor = Executors.newSingleThreadExecutor();
    private Duration timeout = Duration.ofSeconds(10L);
    private ScheduledExecutorService delayer = Executors.newScheduledThreadPool(1);
    private TypedKeyStorageFactory storageFactory = TypedKeyStorageFactory.simple();

    /**
     * Creates a new {@link ServicePipelineBuilder} instance.
     *
     * @return a new {@link ServicePipelineBuilder}.
     */
    public static ServicePipelineBuilder newBuilder() {
        return new ServicePipelineBuilder();
    }

    /**
     * Sets the executor for the service pipeline.
     *
     * @param executor the executor to set.
     * @return this builder instance.
     */
    public ServicePipelineBuilder executor(final Executor executor) {
        this.executor = Preconditions.argumentNotNull(executor, "executor");
        return this;
    }

    /**
     * Sets the timeout duration for the service pipeline.
     *
     * @param timeout the timeout duration to set.
     * @return this builder instance.
     */
    public ServicePipelineBuilder timeout(final Duration timeout) {
        this.timeout = Preconditions.argumentNotNull(timeout, "timeout");
        return this;
    }

    /**
     * Sets the scheduled executor service for the service pipeline.
     *
     * @param delayer the scheduled executor service to set.
     * @return this builder instance.
     */
    public ServicePipelineBuilder delayer(final ScheduledExecutorService delayer) {
        this.delayer = Preconditions.argumentNotNull(delayer, "delayer");
        return this;
    }

    public ServicePipelineBuilder storageFactory(final TypedKeyStorageFactory storageFactory) {
        this.storageFactory = Preconditions.argumentNotNull(storageFactory, "storageFactory");
        return this;
    }

    /**
     * Builds a new {@link ServicePipeline} instance with the configured parameters.
     *
     * @return a new {@link ServicePipeline}.
     */
    public ServicePipeline build() {
        return new ServicePipeline(this.executor, this.timeout, this.delayer, this.storageFactory);
    }

    private ServicePipelineBuilder() {}
}
