package net.infumia.frame.service;

import java.time.Duration;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import net.infumia.frame.Preconditions;

public final class ServicePipelineBuilder {

    private Executor executor = Executors.newSingleThreadExecutor();
    private Duration timeout = Duration.ofSeconds(10L);
    private ScheduledExecutorService delayer = Executors.newScheduledThreadPool(1);

    public static ServicePipelineBuilder newBuilder() {
        return new ServicePipelineBuilder();
    }

    public ServicePipelineBuilder executor(final Executor executor) {
        this.executor = Preconditions.argumentNotNull(executor, "executor");
        return this;
    }

    public ServicePipelineBuilder timeout(final Duration timeout) {
        this.timeout = Preconditions.argumentNotNull(timeout, "timeout");
        return this;
    }

    public ServicePipelineBuilder delayer(final ScheduledExecutorService delayer) {
        this.delayer = Preconditions.argumentNotNull(delayer, "delayer");
        return this;
    }

    public ServicePipeline build() {
        return new ServicePipeline(this.executor, this.timeout, this.delayer);
    }

    private ServicePipelineBuilder() {}
}
