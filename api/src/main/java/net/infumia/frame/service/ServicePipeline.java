package net.infumia.frame.service;

import io.leangen.geantyref.TypeToken;
import java.time.Duration;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import org.jetbrains.annotations.NotNull;

public final class ServicePipeline {

    final Executor executor;
    final Duration timeout;
    final ScheduledExecutorService delayer;

    ServicePipeline(
        @NotNull final Executor executor,
        @NotNull final Duration timeout,
        @NotNull final ScheduledExecutorService delayer
    ) {
        this.executor = executor;
        this.timeout = timeout;
        this.delayer = delayer;
    }

    @NotNull
    public <Context, Result> ServiceRepository<Context, Result> create(
        @NotNull final TypeToken<? extends Service<Context, Result>> type,
        @NotNull final Service<Context, Result> defaultImplementation
    ) {
        return new ServiceRepository<>(this, type, defaultImplementation);
    }
}
