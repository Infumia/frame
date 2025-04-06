package net.infumia.frame.util;

import java.time.Duration;
import org.jetbrains.annotations.NotNull;

public final class Ticks {

    public static int toTicks(@NotNull final Duration duration) {
        return (int) (duration.toMillis() / 50L);
    }

    private Ticks() {
        throw new IllegalStateException("Utility class!");
    }
}
