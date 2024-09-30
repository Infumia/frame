package net.infumia.inv.core.state.value;

import java.util.function.Supplier;
import net.infumia.inv.api.state.value.StateValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class StateValueComputed<T> implements StateValue<T> {

    private final Supplier<T> computation;

    public StateValueComputed(@NotNull final Supplier<T> computation) {
        this.computation = computation;
    }

    @Nullable
    @Override
    public T value() {
        return this.computation.get();
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
