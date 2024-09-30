package net.infumia.frame.util;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Cache<T> implements Supplier<T> {

    @NotNull
    private final Supplier<T> supplier;

    @Nullable
    private volatile T value;

    private Cache(@NotNull final Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @NotNull
    public static <T> Cache<T> of(@NotNull final Supplier<T> supplier) {
        return new Cache<>(Objects.requireNonNull(supplier, "supplier"));
    }

    @Override
    public T get() {
        T val = this.value;
        if (val == null) {
            synchronized (this) {
                val = this.value;
                if (val == null) {
                    val = this.supplier.get();
                    this.value = val;
                }
            }
        }
        return val;
    }

    @NotNull
    public Optional<T> ifPresent() {
        return Optional.ofNullable(this.value);
    }

    public void invalidate() {
        this.value = null;
    }
}
