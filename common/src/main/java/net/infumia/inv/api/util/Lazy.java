package net.infumia.inv.api.util;

import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

public final class Lazy<T> implements Supplier<T> {

    @NotNull
    private final Cache<T> delegate;

    private Lazy(@NotNull final Cache<T> delegate) {
        this.delegate = delegate;
    }

    @NotNull
    public static <T> Lazy<T> of(@NotNull final Supplier<T> supplier) {
        return new Lazy<>(Cache.of(supplier));
    }

    @Override
    public T get() {
        return this.delegate.get();
    }
}
