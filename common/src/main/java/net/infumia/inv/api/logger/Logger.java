package net.infumia.inv.api.logger;

import org.jetbrains.annotations.NotNull;

public interface Logger {
    boolean isDebugEnabled();

    void enableDebug(boolean enable);

    void error(
        @NotNull Throwable throwable,
        @NotNull String message,
        @NotNull Object @NotNull... args
    );

    void error(@NotNull String message, @NotNull Object @NotNull... args);

    void warn(@NotNull String message, @NotNull Object @NotNull... args);

    void info(@NotNull String message, @NotNull Object @NotNull... args);

    void debug(@NotNull String message, @NotNull Object @NotNull... args);
}
