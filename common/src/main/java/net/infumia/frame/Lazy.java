package net.infumia.frame;

import java.util.function.Supplier;

public final class Lazy<T> implements Supplier<T> {

    private volatile Supplier<T> supplier;
    private volatile boolean initialized = false;
    private T value;

    private Lazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    private Lazy(T value) {
        this.value = value;
        this.initialized = true;
    }

    public static <T> Lazy<T> of(final Supplier<T> supplier) {
        return new Lazy<>(Preconditions.argumentNotNull(supplier, "supplier"));
    }

    public static <T> Lazy<T> of(final T value) {
        return new Lazy<>(Preconditions.argumentNotNull(value, "value"));
    }

    @Override
    public T get() {
        if (!this.initialized) {
            synchronized (this) {
                if (!this.initialized) {
                    T t = this.supplier.get();

                    this.value = t;
                    this.initialized = true;

                    this.supplier = null;
                    return t;
                }
            }
        }
        return this.value;
    }
}
