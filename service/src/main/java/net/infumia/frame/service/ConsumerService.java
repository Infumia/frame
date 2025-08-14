package net.infumia.frame.service;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.infumia.frame.Preconditions;

/**
 * A service that consumes a context.
 *
 * @param <Context> the context type.
 */
public interface ConsumerService<Context>
    extends
        Service<Context, ConsumerService.State>,
        BiConsumer<CompletableFuture<ConsumerService.State>, Context>,
        Consumer<Context> {
    @Override
    default void accept(final Context ctx) {}

    @Override
    default void accept(final CompletableFuture<ConsumerService.State> future, final Context ctx) {
        Preconditions.argumentNotNull(future, "future");

        this.accept(ctx);
        future.complete(ConsumerService.State.CONTINUE);
    }

    @Override
    default CompletableFuture<ConsumerService.State> handle(final Context ctx) {
        final CompletableFuture<ConsumerService.State> future = new CompletableFuture<>();
        try {
            this.accept(future, ctx);
        } catch (final Throwable throwable) {
            future.completeExceptionally(throwable);
        }
        return future;
    }

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
