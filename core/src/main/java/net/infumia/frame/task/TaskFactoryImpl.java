package net.infumia.frame.task;

import java.io.Closeable;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import net.infumia.frame.logger.Logger;
import net.infumia.frame.util.RunnableThrowable;
import net.infumia.frame.util.Ticks;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
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
    public Closeable sync(@NotNull final RunnableThrowable task) {
        final BukkitTask bukkitTask = Bukkit.getScheduler()
            .runTask(this.plugin, () -> {
                try {
                    task.run();
                } catch (final Throwable e) {
                    this.logger.error(e, "An error occurred while running a sync task.");
                }
            });
        return bukkitTask::cancel;
    }

    @NotNull
    @Override
    public CompletableFuture<?> syncFuture(@NotNull final RunnableThrowable task) {
        final CompletableFuture<?> future = new CompletableFuture<>();
        Bukkit.getScheduler()
            .runTask(this.plugin, () -> {
                try {
                    task.run();
                    future.complete(null);
                } catch (final Throwable e) {
                    this.logger.error(e, "An error occurred while running a sync task.");
                    future.completeExceptionally(e);
                }
            });
        return future;
    }

    @NotNull
    @Override
    public Closeable sync(
        @NotNull final RunnableThrowable task,
        @NotNull final Duration delay,
        @NotNull final Duration period
    ) {
        final BukkitTask bukkitTask = Bukkit.getScheduler()
            .runTaskTimer(
                this.plugin,
                () -> {
                    try {
                        task.run();
                    } catch (final Throwable e) {
                        this.logger.error(e, "An error occurred while running a sync task.");
                    }
                },
                Ticks.toTicks(delay),
                Ticks.toTicks(period)
            );
        return bukkitTask::cancel;
    }
}
