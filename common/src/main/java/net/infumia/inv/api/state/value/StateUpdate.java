package net.infumia.inv.api.state.value;

import net.infumia.inv.api.state.State;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class StateUpdate<T> {

    @NotNull
    private final State<T> state;

    @Nullable
    private final T oldValue;

    @NotNull
    private final StateValue<T> value;

    public StateUpdate(
        @NotNull final State<T> state,
        @Nullable final T oldValue,
        @NotNull final StateValue<T> value
    ) {
        this.state = state;
        this.oldValue = oldValue;
        this.value = value;
    }

    @NotNull
    public State<T> state() {
        return this.state;
    }

    @Nullable
    public T oldValue() {
        return this.oldValue;
    }

    @NotNull
    public StateValue<T> value() {
        return this.value;
    }
}
