package net.infumia.frame.task;

import java.io.Closeable;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;
import net.infumia.frame.util.Ticks;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

// TODO: portlek, Add paper's folia support.
public final class TaskFactoryImpl implements TaskFactory {

    private final Plugin plugin;

    public TaskFactoryImpl(@NotNull final Plugin plugin) {
        this.plugin = plugin;
    }

    @NotNull
    @Override
    public <T> CompletableFuture<T> handleFuture(
        @NotNull final Supplier<CompletableFuture<T>> task
    ) {
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
                        }
                    })
            );
        return future;
    }

    @NotNull
    @Override
    public Executor asExecutor() {
        return command -> Bukkit.getScheduler().runTask(this.plugin, command);
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
