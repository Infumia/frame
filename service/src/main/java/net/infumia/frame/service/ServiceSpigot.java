package net.infumia.frame.service;

import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import net.infumia.frame.Preconditions;

final class ServiceSpigot<Context, Result> {

    private final ServicePipeline pipeline;
    private final ServiceRepository<Context, Result> repository;
    private final Context context;

    ServiceSpigot(
        final ServicePipeline pipeline,
        final ServiceRepository<Context, Result> repository,
        final Context context
    ) {
        this.pipeline = pipeline;
        this.repository = repository;
        this.context = context;
    }

    CompletableFuture<Result> complete() {
        return this.completeInternally(Runnable::run);
    }

    CompletableFuture<Result> completeAsync() {
        return this.completeInternally(this.pipeline.executor);
    }

    private CompletableFuture<Result> completeInternally(final Executor executor) {
        final CompletableFuture<Result> future = new CompletableFuture<>();
        final ScheduledFuture<?> delayer = this.scheduleTimeout(future);
        final AtomicBoolean isConsumerService = new AtomicBoolean(false);

        executor.execute(() ->
            this.processServices(isConsumerService).whenComplete((result, throwable) -> {
                    if (delayer.cancel(true)) {
                        if (throwable == null) {
                            future.complete(result);
                        } else {
                            future.completeExceptionally(throwable);
                        }
                    }
                })
        );

        return future.thenApply(result -> this.checkFinalResult(isConsumerService, result));
    }

    private CompletableFuture<Result> processServices(final AtomicBoolean isConsumerService) {
        CompletableFuture<Result> job = CompletableFuture.completedFuture(null);
        final LinkedList<ServiceWrapper<Context, Result>> queue = this.repository.queue();
        ServiceWrapper<Context, Result> wrapper;

        while ((wrapper = queue.pollLast()) != null) {
            job = this.processService(isConsumerService, wrapper, job);
        }
        return job;
    }

    private CompletableFuture<Result> processService(
        final AtomicBoolean isConsumerService,
        final ServiceWrapper<Context, Result> wrapper,
        final CompletableFuture<Result> job
    ) {
        final Service<Context, Result> service = wrapper.implementation;
        isConsumerService.set(service instanceof ConsumerService);
        if (!wrapper.passes(this.context)) {
            return job;
        }
        return job.thenCompose(result ->
            this.shouldContinue(isConsumerService, result)
                ? service.handle(this.context)
                : CompletableFuture.completedFuture(result)
        );
    }

    private boolean shouldContinue(final AtomicBoolean isConsumerService, final Result result) {
        if (this.isCancelled()) {
            return false;
        }
        if (result == null) {
            return true;
        }
        return isConsumerService.get() && result != ConsumerService.State.FINISHED;
    }

    @SuppressWarnings("unchecked")
    private Result checkFinalResult(final AtomicBoolean isConsumerService, final Result result) {
        if (isConsumerService.get()) {
            return (Result) ConsumerService.State.FINISHED;
        }
        return Preconditions.argumentNotNull(result, "No service consumed the context.");
    }

    private boolean isCancelled() {
        return this.context instanceof Cancellable && ((Cancellable) this.context).cancelled();
    }

    private ScheduledFuture<?> scheduleTimeout(final CompletableFuture<?> future) {
        return this.pipeline.delayer.schedule(
                () -> this.tryTimeout(future),
                this.pipeline.timeout.toMillis(),
                TimeUnit.MILLISECONDS
            );
    }

    private void tryTimeout(final CompletableFuture<?> future) {
        if (future.isDone()) {
            return;
        }
        future.completeExceptionally(
            new TimeoutException(
                String.format(
                    "Service '%s' could not complete in time %sms",
                    this.repository.serviceType.getType().getTypeName(),
                    this.pipeline.timeout.toMillis()
                )
            )
        );
    }
}
