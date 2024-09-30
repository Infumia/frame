package net.infumia.inv.core.state.value;

import net.infumia.inv.api.InitialDataHolder;
import net.infumia.inv.api.state.value.StateValue;
import net.infumia.inv.api.typedkey.TypedKeyStorageImmutable;
import net.infumia.inv.api.util.Lazy;
import net.infumia.inv.api.util.Preconditions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class StateValueInitial<T> implements StateValue<T> {

    private final String stateKey;
    private final InitialDataHolder initialDataHolder;
    private StateValue<T> backingValue;

    public StateValueInitial(
        @NotNull final InitialDataHolder initialDataHolder,
        @NotNull final String stateKey
    ) {
        this.stateKey = stateKey;
        this.initialDataHolder = initialDataHolder;
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
                final TypedKeyStorageImmutable initialData = Preconditions.stateNotNull(
                    this.initialDataHolder.initialData(),
                    "Initial data not found even tough there is a initial state '%s' registered!",
                    key
                );
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
