package net.infumia.frame.task;

import java.io.Closeable;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

public interface TaskFactory {
    @NotNull
    <T> CompletableFuture<T> handleFuture(@NotNull Supplier<CompletableFuture<T>> task);

    @NotNull
    Closeable run(@NotNull Runnable task);

    @NotNull
    CompletableFuture<?> runAsFuture(@NotNull Runnable task);

    @NotNull
    Closeable run(@NotNull Runnable task, @NotNull Duration delay, @NotNull Duration period);
}
