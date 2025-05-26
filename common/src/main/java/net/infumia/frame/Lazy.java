package net.infumia.frame;

import java.util.function.Supplier;

/**
 * A lazy is a class that is used to create lazy objects.
 *
 * @param <T> the type of the lazy object
 */
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

    /**
     * Creates a new lazy object.
     *
     * @param supplier the supplier
     * @return the new lazy object
     */
    public static <T> Lazy<T> of(final Supplier<T> supplier) {
        return new Lazy<>(Preconditions.argumentNotNull(supplier, "supplier"));
    }

    /**
     * Creates a new lazy object.
     *
     * @param value the value
     * @return the new lazy object
     */
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
