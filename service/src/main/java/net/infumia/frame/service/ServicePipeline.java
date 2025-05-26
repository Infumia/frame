package net.infumia.frame.service;

import io.leangen.geantyref.TypeToken;
import java.time.Duration;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;

public final class ServicePipeline {

    final Executor executor;
    final Duration timeout;
    final ScheduledExecutorService delayer;

    ServicePipeline(
        final Executor executor,
        final Duration timeout,
        final ScheduledExecutorService delayer
    ) {
        this.executor = executor;
        this.timeout = timeout;
        this.delayer = delayer;
    }

    public <Context, Result> ServiceRepository<Context, Result> create(
        final TypeToken<? extends Service<Context, Result>> type,
        final Service<Context, Result> defaultImplementation
    ) {
        return new ServiceRepository<>(this, type, defaultImplementation);
    }
}
