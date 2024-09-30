package net.infumia.frame.state;

import net.infumia.frame.state.value.StateValueFactory;
import org.jetbrains.annotations.NotNull;

public final class StateInitialImpl<T> extends StateImpl<T> implements StateInitialRich<T> {

    private final String key;

    public StateInitialImpl(
        final long id,
        @NotNull final StateValueFactory<T> valueFactory,
        @NotNull final String key
    ) {
        super(id, valueFactory);
        this.key = key;
    }

    @Override
    public String key() {
        return this.key;
    }
}
