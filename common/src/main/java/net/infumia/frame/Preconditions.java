package net.infumia.frame;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A utility class for preconditions.
 */
public final class Preconditions {

    /**
     * Checks if an argument is null.
     *
     * @param check The check.
     * @param message The message.
     * @param args The arguments.
     * @throws IllegalArgumentException If the check is false.
     */
    @Contract("false, _, _ -> fail")
    public static void argument(
        final boolean check,
        @NotNull final String message,
        final Object @NotNull... args
    ) {
        if (!check) {
            throw new IllegalArgumentException(String.format(message, args));
        }
    }

    /**
     * Checks if an argument is not null.
     *
     * @param object The object.
     * @param message The message.
     * @param args The arguments.
     * @return The object.
     * @throws IllegalArgumentException If the object is null.
     */
    @Contract("null, _, _ -> fail; !null, _, _ -> param1")
    public static <T> T argumentNotNull(
        @Nullable final T object,
        @NotNull final String message,
        final Object @NotNull... args
    ) {
        Preconditions.argument(object != null, message, args);
        return object;
    }

    /**
     * Checks if a state is valid.
     *
     * @param check The check.
     * @param message The message.
     * @param args The arguments.
     * @throws IllegalStateException If the check is false.
     */
    @Contract("false, _, _ -> fail")
    public static void state(
        final boolean check,
        @NotNull final String message,
        final Object @NotNull... args
    ) {
        if (!check) {
            throw new IllegalStateException(String.format(message, args));
        }
    }

    /**
     * Checks if a state is not null.
     *
     * @param object The object.
     * @param message The message.
     * @param args The arguments.
     * @return The object.
     * @throws IllegalStateException If the object is null.
     */
    @Contract("null, _, _ -> fail; !null, _, _ -> param1")
    public static <T> T stateNotNull(
        @Nullable final T object,
        @NotNull final String message,
        final Object @NotNull... args
    ) {
        Preconditions.state(object != null, message, args);
        return object;
    }

    private Preconditions() {
        throw new IllegalStateException("Utility class!");
    }
}
