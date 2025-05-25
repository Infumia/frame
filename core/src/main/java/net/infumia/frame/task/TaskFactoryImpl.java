package net.infumia.frame.task;

import java.io.Closeable;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import net.infumia.frame.logger.Logger;
import net.infumia.frame.util.Ticks;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

// TODO: portlek, Add paper's folia support.
public final class TaskFactoryImpl implements TaskFactory {

    private final Plugin plugin;
    private final Logger logger;

    public TaskFactoryImpl(@NotNull final Plugin plugin, @NotNull final Logger logger) {
        this.plugin = plugin;
        this.logger = logger;
    }

    @NotNull
    @Override
    public <T> CompletableFuture<T> handleFuture(@NotNull Supplier<CompletableFuture<T>> task) {
        if (Bukkit.isPrimaryThread()) {
            return task.get();
        }
        final CompletableFuture<T> future = new CompletableFuture<>();
        Bukkit.getScheduler()
            .runTask(this.plugin, () ->
                task
                    .get()
                    .whenComplete((result, throwable) -> {
                        if (throwable == null) {
                            future.complete(result);
                        } else {
                            future.completeExceptionally(throwable);
                            this.logger.error(
                                    throwable,
                                    "An error occurred while running a sync task."
                                );
                        }
                    })
            );
        return future;
    }

    @NotNull
    @Override
    public Closeable run(
        @NotNull final Runnable task,
        @NotNull final Duration delay,
        @NotNull final Duration period
    ) {
        return Bukkit.getScheduler()
            .runTaskTimer(this.plugin, task, Ticks.toTicks(delay), Ticks.toTicks(period))::cancel;
    }
}
