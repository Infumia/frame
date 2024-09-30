package net.infumia.frame.state;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import net.infumia.frame.state.value.StateValue;
import net.infumia.frame.state.value.StateValueFactory;
import net.infumia.frame.state.value.StateValueHostHolder;
import net.infumia.frame.state.value.StateValueHostRich;
import net.infumia.frame.state.watcher.StateWatcherAccess;
import net.infumia.frame.state.watcher.StateWatcherUpdate;
import net.infumia.frame.util.Preconditions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StateImpl<T> implements StateRich<T> {

    private final long id;
    private final StateValueFactory<T> valueFactory;

    public StateImpl(final long id, @NotNull final StateValueFactory<T> valueFactory) {
        this.id = id;
        this.valueFactory = valueFactory;
    }

    @Override
    public long id() {
        return this.id;
    }

    @NotNull
    @Override
    public StateValueFactory<T> valueFactory() {
        return this.valueFactory;
    }

    @Nullable
    @Override
    public StateValue<T> manualUpdate(@NotNull final StateValueHostHolder host) {
        return ((StateValueHostRich) host.stateValueHost()).updateStateValue(this);
    }

    @NotNull
    @Override
    public CompletableFuture<StateValue<T>> manualUpdateWait(
        @NotNull final StateValueHostHolder host
    ) {
        return ((StateValueHostRich) host.stateValueHost()).updateStateValueWait(this);
    }

    @Nullable
    @Override
    public T get(@NotNull final StateValueHostHolder host) {
        return ((StateValueHostRich) host.stateValueHost()).accessStateValueOrInitialize(
                this
            ).value();
    }

    @NotNull
    @Override
    public T getOtThrow(@NotNull final StateValueHostHolder host) {
        return Preconditions.stateNotNull(
            this.get(host),
            "Value for state '%s' not found!",
            this.id
        );
    }

    @NotNull
    @Override
    public CompletableFuture<@Nullable T> getWait(@NotNull final StateValueHostHolder host) {
        return ((StateValueHostRich) host.stateValueHost()).accessStateValueOrInitializeWait(
                this
            ).thenApply(StateValue::value);
    }

    @NotNull
    @Override
    public CompletableFuture<T> getOtThrowWait(@NotNull final StateValueHostHolder host) {
        return this.getWait(host).thenApply(value ->
                Preconditions.stateNotNull(value, "Value for state '%s' not found!", this.id)
            );
    }

    @Override
    public void watchAccess(
        @NotNull final StateValueHostHolder host,
        @NotNull final StateWatcherAccess<T> watcher
    ) {
        ((StateValueHostRich) host.stateValueHost()).watchStateAccess(this, watcher);
    }

    @Override
    public void watchUpdate(
        @NotNull final StateValueHostHolder host,
        @NotNull final StateWatcherUpdate<T> watcher
    ) {
        ((StateValueHostRich) host.stateValueHost()).watchStateUpdate(this, watcher);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StateRich)) {
            return false;
        }
        return this.id == ((StateRich<?>) o).id();
    }
}
