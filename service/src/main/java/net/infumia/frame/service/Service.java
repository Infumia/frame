package net.infumia.frame.service;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.typedkey.TypedKeyStorage;

/**
 * Represents a service that handles a context and produces a result asynchronously.
 *
 * @param <C> the type of the context object that this service handles.
 * @param <Result> the type of the result produced by this service.
 */
public interface Service<C, Result> {
    /**
     * Handles the given context and returns a {@link CompletableFuture} that will be completed with the result.
     *
     * @param c the context to handle.
     * @param storage the service storage.
     * @return a {@link CompletableFuture} that will be completed with the result of handling the context.
     */
    CompletableFuture<Result> handle(C c, TypedKeyStorage storage);

    /**
     * Gets the unique key identifying this service.
     *
     * @return the key of the service.
     */
    String key();
}
