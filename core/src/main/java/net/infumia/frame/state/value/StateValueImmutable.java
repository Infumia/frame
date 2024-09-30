package net.infumia.frame.state.value;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class StateValueImmutable<T> implements StateValue<T> {

    private final T value;

    public StateValueImmutable(@NotNull final T value) {
        this.value = value;
    }

    @NotNull
    @Override
    public T value() {
        return this.value;
    }

    @Override
    public void value(@Nullable final T value) {
        throw new UnsupportedOperationException("Immutable state!");
    }

    @Override
    public boolean mutable() {
        return false;
    }
}
