package net.infumia.frame.extension;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import net.infumia.frame.logger.Logger;
import org.jetbrains.annotations.NotNull;

public final class CompletableFutureExtensions {

    @NotNull
    public static <T> CompletableFuture<T> logError(
        @NotNull final CompletableFuture<T> future,
        @NotNull final Logger logger,
        @NotNull final String message,
        @NotNull final Object @NotNull... args
    ) {
        return future.exceptionally(throwable -> {
            if (throwable instanceof CompletionException) {
                throwable = throwable.getCause();
            }
            if (throwable instanceof StackOverflowError) {
                throwable.printStackTrace();
            } else {
                logger.error(throwable, message, args);
            }
            return null;
        });
    }
}
