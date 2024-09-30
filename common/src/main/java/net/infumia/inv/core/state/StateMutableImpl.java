package net.infumia.inv.core.state;

import java.util.concurrent.CompletableFuture;
import net.infumia.inv.api.state.value.StateValue;
import net.infumia.inv.api.state.value.StateValueHostHolder;
import net.infumia.inv.core.state.value.StateValueFactory;
import net.infumia.inv.core.state.value.StateValueHostRich;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class StateMutableImpl<T> extends StateImpl<T> implements StateMutableRich<T> {

    public StateMutableImpl(final long id, @NotNull final StateValueFactory<T> valueFactory) {
        super(id, valueFactory);
    }

    @Nullable
    @Override
    public StateValue<T> set(@NotNull final StateValueHostHolder host, @Nullable final T value) {
        return ((StateValueHostRich) host.stateValueHost()).updateStateValue(this, value);
    }

    @NotNull
    @Override
    public CompletableFuture<@Nullable StateValue<T>> setWait(
        @NotNull final StateValueHostHolder host,
        @Nullable final T value
    ) {
        return ((StateValueHostRich) host.stateValueHost()).updateStateValueWait(this, value);
    }
}
