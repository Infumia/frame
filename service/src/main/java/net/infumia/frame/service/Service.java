package net.infumia.frame.service;

import java.util.concurrent.CompletableFuture;

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
     * @param context the context to handle.
     * @return a {@link CompletableFuture} that will be completed with the result of handling the context.
     */
    CompletableFuture<Result> handle(Context context);

    /**
     * Gets the unique key identifying this service.
     *
     * @return the key of the service.
     */
    String key();
}
