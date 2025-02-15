package net.infumia.frame.util;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public final class Cache<T> implements Supplier<T> {

    private final Supplier<T> supplier;
    private volatile T value;

    private Cache(final Supplier<T> supplier) {
        this.supplier = Objects.requireNonNull(supplier, "supplier");
    }

    public static <T> Cache<T> of(final Supplier<T> supplier) {
        return new Cache<>(supplier);
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

    public Optional<T> ifPresent() {
        return Optional.ofNullable(this.value);
    }

    public void invalidate() {
        this.value = null;
    }
}
