package net.infumia.frame.service;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.infumia.frame.Preconditions;

public interface ConsumerService<Context>
    extends
        Service<Context, ConsumerService.State>,
        BiConsumer<CompletableFuture<ConsumerService.State>, Context>,
        Consumer<Context> {
    @Override
    default void accept(final Context ctx) {}

    @Override
    default void accept(final CompletableFuture<State> future, final Context ctx) {
        Preconditions.argumentNotNull(future, "future");

        this.accept(ctx);
        future.complete(State.CONTINUE);
    }

    @Override
    default CompletableFuture<State> handle(final Context ctx) {
        final CompletableFuture<State> future = new CompletableFuture<>();
        try {
            this.accept(future, ctx);
        } catch (final Throwable throwable) {
            future.completeExceptionally(throwable);
        }
        return future;
    }

    enum State {
        CONTINUE,
        FINISHED,
    }
}
