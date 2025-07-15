package net.infumia.frame.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import net.infumia.frame.Preconditions;
import net.infumia.frame.typedkey.TypedKeyStorage;
import net.infumia.frame.typedkey.TypedKeyStorageFactory;

@SuppressWarnings("unchecked")
final class ServiceSpigot<Context, Result> {

    private final ServicePipeline pipeline;
    private final ServiceRepository<Context, Result> repository;
    private final TypedKeyStorageFactory storageFactory;

    ServiceSpigot(
        final ServicePipeline pipeline,
        final ServiceRepository<Context, Result> repository,
        final TypedKeyStorageFactory storageFactory
    ) {
        this.pipeline = pipeline;
        this.repository = repository;
        this.storageFactory = storageFactory;
    }

    CompletableFuture<Result> complete(final Context context) {
        return this.completeInternally(context, Runnable::run);
    }

    CompletableFuture<Result> completeAsync(final Context context) {
        return this.completeInternally(context, this.pipeline.executor);
    }

    private CompletableFuture<Result> completeInternally(
        final Context context,
        final Executor executor
    ) {
        final CompletableFuture<Result> future = new CompletableFuture<>();
        final ScheduledFuture<?> delayer = this.scheduleTimeout(future);
        final AtomicReference<Result> firstResult = new AtomicReference<>();
        final TypedKeyStorage storage = this.storageFactory.create(new HashMap<>());

        executor.execute(() ->
            this.processServices(context, storage, firstResult).whenComplete(
                    (result, throwable) -> {
                        if (delayer.cancel(true)) {
                            if (throwable == null) {
                                future.complete(result);
                            } else {
                                future.completeExceptionally(throwable);
                            }
                        }
                    }
                )
        );

        return future
            .thenApply(result -> this.checkFinalResult(firstResult))
            .whenComplete((__, ___) -> storage.clear());
    }

    private CompletableFuture<Result> processServices(
        final Context context,
        final TypedKeyStorage storage,
        final AtomicReference<Result> firstResult
    ) {
        final LinkedList<ServiceWrapper<Context, Result>> queue = this.repository.queue();
        CompletableFuture<Result> job = CompletableFuture.completedFuture(null);

        ServiceWrapper<Context, Result> currentWrapper;
        while ((currentWrapper = queue.pollLast()) != null) {
            final ServiceWrapper<Context, Result> wrapper = currentWrapper;
            job = job.thenCompose(previousResult -> {
                if (this.shouldStop(context, previousResult)) {
                    return CompletableFuture.completedFuture(previousResult);
                }

                if (!wrapper.passes(context)) {
                    return CompletableFuture.completedFuture(previousResult);
                }

                return wrapper.implementation
                    .handle(context, storage)
                    .thenApply(result -> {
                        if (wrapper.isResultService() && firstResult.get() == null) {
                            firstResult.set(result);
                        }
                        return result;
                    });
            });
        }
        return job.thenApply(lastResult -> firstResult.get());
    }

    private boolean shouldStop(final Context context, final Result result) {
        if (this.isCancelled(context) || result == ConsumerService.State.FINISHED) {
            return true;
        }
        if (result == null) {
            return false;
        }
        return result != ConsumerService.State.CONTINUE;
    }

    private Result checkFinalResult(final AtomicReference<Result> firstResult) {
        final Result result = firstResult.get();
        if (result == null) {
            return (Result) ConsumerService.State.FINISHED;
        }
        return Preconditions.argumentNotNull(result, "No service consumed the context.");
    }

    private boolean isCancelled(final Context context) {
        return context instanceof Cancellable && ((Cancellable) context).cancelled();
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
