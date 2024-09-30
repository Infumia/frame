package net.infumia.frame.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Preconditions {

    @Contract("false, _, _ -> fail")
    public static void argument(
        final boolean check,
        @NotNull final String message,
        @NotNull final Object @NotNull... args
    ) {
        if (!check) {
            throw new IllegalArgumentException(String.format(message, args));
        }
    }

    @NotNull
    @Contract("null, _, _ -> fail")
    public static <T> T argumentNotNull(
        @Nullable final T object,
        @NotNull final String message,
        @NotNull final Object @NotNull... args
    ) {
        Preconditions.argument(object != null, message, args);
        return object;
    }

    @Contract("false, _, _ -> fail")
    public static void state(
        final boolean check,
        @NotNull final String message,
        @NotNull final Object @NotNull... args
    ) {
        if (!check) {
            throw new IllegalStateException(String.format(message, args));
        }
    }

    @NotNull
    @Contract("null, _, _ -> fail")
    public static <T> T stateNotNull(
        @Nullable final T object,
        @NotNull final String message,
        @NotNull final Object @NotNull... args
    ) {
        Preconditions.state(object != null, message, args);
        return object;
    }

    private Preconditions() {
        throw new IllegalStateException("Utility class!");
    }
}
