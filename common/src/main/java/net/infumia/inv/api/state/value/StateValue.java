package net.infumia.inv.api.state.value;

import org.jetbrains.annotations.Nullable;

public interface StateValue<T> {
    @Nullable
    T value();

    void value(@Nullable T value);

    boolean mutable();
}
