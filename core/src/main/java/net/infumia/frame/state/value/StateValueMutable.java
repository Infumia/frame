package net.infumia.frame.state.value;

import org.jetbrains.annotations.Nullable;

public final class StateValueMutable<T> implements StateValue<T> {

    private T value;

    public StateValueMutable(@Nullable final T value) {
        this.value = value;
    }

    @Override
    @Nullable
    public T value() {
        return this.value;
    }

    @Override
    public void value(@Nullable final T value) {
        this.value = value;
    }

    @Override
    public boolean mutable() {
        return true;
    }
}
