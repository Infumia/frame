package net.infumia.frame.service;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.typedkey.TypedKeyStorage;

/**
 * Represents a service that handles a context and produces a result asynchronously.
 *
 * @param <Context> the type of the context object that this service handles.
 * @param <Result> the type of the result produced by this service.
 */
public interface Service<Context, Result> {
    /**
     * Handles the given context and returns a {@link CompletableFuture} that will be completed with the result.
     *
     * @param ctx the context to handle.
     * @param storage the service storage.
     * @return a {@link CompletableFuture} that will be completed with the result of handling the context.
     */
    default CompletableFuture<Result> handle(final Context ctx, final TypedKeyStorage storage) {
        return this.handle(ctx);
    }

    /**
     * Handles the given context and returns a {@link CompletableFuture} that will be completed with the result.
     *
     * @param ctx the context to handle.
     * @return a {@link CompletableFuture} that will be completed with the result of handling the context.
     */
    default CompletableFuture<Result> handle(final Context ctx) {
        throw new UnsupportedOperationException("Implement #handle(Context)");
    }

    /**
     * Gets the unique key identifying this service.
     *
     * @return the key of the service.
     */
    String key();
}
