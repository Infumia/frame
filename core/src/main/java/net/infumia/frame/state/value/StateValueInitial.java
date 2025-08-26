package net.infumia.frame.state.value;

import net.infumia.frame.Lazy;
import net.infumia.frame.Preconditions;
import net.infumia.frame.context.ContextBase;
import net.infumia.frame.typedkey.TypedKey;
import net.infumia.frame.typedkey.TypedKeyStorageImmutable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class StateValueInitial<T> implements StateValue<T> {

    private final ContextBase context;
    private final Lazy<T> backingValue;

    public StateValueInitial(
        @NotNull final ContextBase context,
        @NotNull final TypedKey<T> stateKey
    ) {
        this.context = context;
        this.backingValue = this.createBackingValue(stateKey);
    }

    @Nullable
    @Override
    public T value() {
        return this.backingValue.get();
    }

    @Override
    public void value(@Nullable final T value) {
        throw new UnsupportedOperationException("Immutable state!");
    }

    @NotNull
    private Lazy<T> createBackingValue(@NotNull final TypedKey<T> key) {
        return Lazy.of(() -> {
            final TypedKeyStorageImmutable initialData = this.context.initialData();
            final T value = Preconditions.stateNotNull(
                initialData.get(key),
                "No initial data found for state '%s'",
                key
            );
            try {
                return value;
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
        });
    }
}
