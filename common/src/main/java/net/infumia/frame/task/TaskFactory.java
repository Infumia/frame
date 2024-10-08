package net.infumia.frame.task;

import java.io.Closeable;
import java.time.Duration;
import net.infumia.frame.util.RunnableThrowable;
import org.jetbrains.annotations.NotNull;

public interface TaskFactory {
    @NotNull
    Closeable sync(@NotNull RunnableThrowable task);

    @NotNull
    Closeable sync(
        @NotNull RunnableThrowable task,
        @NotNull Duration delay,
        @NotNull Duration period
    );
}
