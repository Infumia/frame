package net.infumia.frame.state.value;

import net.infumia.frame.Lazy;
import net.infumia.frame.Preconditions;
import net.infumia.frame.context.ContextBase;
import net.infumia.frame.typedkey.TypedKeyStorageImmutable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class StateValueInitial<T> implements StateValue<T> {

    private final String stateKey;
    private final ContextBase context;
    private StateValue<T> backingValue;

    public StateValueInitial(@NotNull final ContextBase context, @NotNull final String stateKey) {
        this.stateKey = stateKey;
        this.context = context;
        this.backingValue = this.createBackingValue(stateKey);
    }

    @Nullable
    @Override
    public T value() {
        return this.backingValue.value();
    }

    @Override
    public void value(@Nullable final T value) {
        this.backingValue.value(value);
    }

    @Override
    public boolean mutable() {
        return this.backingValue.mutable();
    }

    public void reset() {
        this.backingValue = this.createBackingValue(this.stateKey);
    }

    @SuppressWarnings("unchecked")
    private StateValue<T> createBackingValue(@NotNull final String key) {
        return new StateValueComputed<>(
            Lazy.of(() -> {
                final TypedKeyStorageImmutable initialData = this.context.initialData();
                final Object value = Preconditions.stateNotNull(
                    initialData.get(key),
                    "No initial data found for state '%s'",
                    key
                );
                try {
                    return (T) value;
                } catch (final ClassCastException e) {
                    throw new RuntimeException(
                        String.format(
                            "Invalid data type '%s' for state '%s'!",
                            value.getClass().getSimpleName(),
                            key
                        ),
                        e
                    );
                }
            })
        );
    }
}
