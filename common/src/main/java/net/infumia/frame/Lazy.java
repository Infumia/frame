package net.infumia.frame;

import java.util.Objects;
import java.util.function.Supplier;

public final class Lazy<T> implements Supplier<T> {

    private final Cache<T> delegate;

    private Lazy(final Cache<T> delegate) {
        this.delegate = Objects.requireNonNull(delegate, "delegate");
    }

    public static <T> Lazy<T> of(final Supplier<T> supplier) {
        return new Lazy<>(Cache.of(supplier));
    }

    @Override
    public T get() {
        return this.delegate.get();
    }
}
