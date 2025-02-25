package net.infumia.frame;

import java.util.Objects;
import org.jetbrains.annotations.Contract;

public final class Preconditions {

    @Contract("false, _, _ -> fail")
    public static void argument(final boolean check, final String message, final Object... args) {
        Objects.requireNonNull(args, "args");
        Objects.requireNonNull(message, "message");

        if (!check) {
            throw new IllegalArgumentException(String.format(message, args));
        }
    }

    @Contract("null, _, _ -> fail")
    public static <T> T argumentNotNull(
        final T object,
        final String message,
        final Object... args
    ) {
        Objects.requireNonNull(args, "args");
        Objects.requireNonNull(message, "message");

        Preconditions.argument(object != null, message, args);
        return object;
    }

    @Contract("false, _, _ -> fail")
    public static void state(final boolean check, final String message, final Object... args) {
        Objects.requireNonNull(args, "args");
        Objects.requireNonNull(message, "message");

        if (!check) {
            throw new IllegalStateException(String.format(message, args));
        }
    }

    @Contract("null, _, _ -> fail")
    public static <T> T stateNotNull(final T object, final String message, final Object... args) {
        Objects.requireNonNull(args, "args");
        Objects.requireNonNull(message, "message");

        Preconditions.state(object != null, message, args);
        return object;
    }

    private Preconditions() {
        throw new IllegalStateException("Utility class!");
    }
}
