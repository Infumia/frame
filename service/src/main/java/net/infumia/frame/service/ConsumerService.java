package net.infumia.frame.service;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public interface ConsumerService<Context>
    extends
        Service<Context, ConsumerService.State>,
        BiConsumer<CompletableFuture<ConsumerService.State>, Context>,
        Consumer<Context> {
    @Override
    @ApiStatus.OverrideOnly
    default void accept(final Context ctx) {}

    @Override
    @ApiStatus.OverrideOnly
    default void accept(@NotNull final CompletableFuture<State> future, final Context ctx) {
        this.accept(ctx);
        future.complete(State.CONTINUE);
    }

    @NotNull
    @Override
    @ApiStatus.NonExtendable
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
