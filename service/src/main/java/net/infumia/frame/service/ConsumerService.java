package net.infumia.frame.service;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.Preconditions;
import net.infumia.frame.typedkey.TypedKeyStorage;

/**
 * A service that consumes a context.
 *
 * @param <Context> the context type.
 */
public interface ConsumerService<Context> extends Service<Context, ConsumerService.State> {
    @Override
    default CompletableFuture<State> handle(final Context ctx, final TypedKeyStorage storage) {
        Preconditions.argumentNotNull(storage, "storage");

        final CompletableFuture<State> future = new CompletableFuture<>();
        try {
            this.accept(future, ctx, storage);
        } catch (final Throwable throwable) {
            future.completeExceptionally(throwable);
        }
        return future;
    }

    default void accept(
        final CompletableFuture<State> future,
        final Context ctx,
        final TypedKeyStorage storage
    ) {
        Preconditions.argumentNotNull(future, "future");
        Preconditions.argumentNotNull(storage, "storage");

        this.accept(ctx, storage);
        future.complete(State.CONTINUE);
    }

    default void accept(final Context ctx, final TypedKeyStorage storage) {}

    /**
     * Represents the state of a {@link ConsumerService}.
     */
    enum State {
        /**
         * Indicates that the service should continue processing.
         */
        CONTINUE,
        /**
         * Indicates that the service has finished processing.
         */
        FINISHED,
    }
}
