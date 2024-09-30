package net.infumia.inv.api.service;

import java.time.Duration;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import org.jetbrains.annotations.NotNull;

public final class ServicePipelineBuilder {

    private Executor executor = Executors.newSingleThreadExecutor();
    private Duration timeout = Duration.ofSeconds(10L);
    private ScheduledExecutorService delayer = Executors.newScheduledThreadPool(1);

    @NotNull
    public static ServicePipelineBuilder newBuilder() {
        return new ServicePipelineBuilder();
    }

    @NotNull
    public ServicePipelineBuilder executor(@NotNull final Executor executor) {
        this.executor = executor;
        return this;
    }

    @NotNull
    public ServicePipelineBuilder timeout(@NotNull final Duration timeout) {
        this.timeout = timeout;
        return this;
    }

    @NotNull
    public ServicePipelineBuilder delayer(@NotNull final ScheduledExecutorService delayer) {
        this.delayer = delayer;
        return this;
    }

    @NotNull
    public ServicePipeline build() {
        return new ServicePipeline(this.executor, this.timeout, this.delayer);
    }

    private ServicePipelineBuilder() {}
}
