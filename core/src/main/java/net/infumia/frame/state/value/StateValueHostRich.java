package net.infumia.frame.state.value;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import net.infumia.frame.state.StateMutableRich;
import net.infumia.frame.state.StateRich;
import net.infumia.frame.state.watcher.StateWatcherAccess;
import net.infumia.frame.state.watcher.StateWatcherUpdate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface StateValueHostRich extends StateValueHost {
    @NotNull
    Map<StateRich<Object>, StateValue<Object>> stateValues();

    @NotNull
    <T> StateValue<T> accessStateValue(@NotNull StateRich<T> state);

    @NotNull
    <T> StateValue<T> updateStateValue(@NotNull StateMutableRich<T> state, @Nullable T value);

    @NotNull
    <T> StateValue<T> updateStateValue(@NotNull StateRich<T> state);

    @NotNull
    <T> CompletableFuture<@NotNull StateValue<T>> accessStateValueWait(@NotNull StateRich<T> state);

    @NotNull
    <T> CompletableFuture<@NotNull StateValue<T>> updateStateValueWait(
        @NotNull StateMutableRich<T> state,
        @Nullable T value
    );

    @NotNull
    <T> CompletableFuture<@NotNull StateValue<T>> updateStateValueWait(@NotNull StateRich<T> state);

    <T> void watchStateAccess(@NotNull StateRich<T> state, @NotNull StateWatcherAccess<T> watcher);

    <T> void watchStateUpdate(@NotNull StateRich<T> state, @NotNull StateWatcherUpdate<T> watcher);

    <T> void initializeState(@NotNull StateRich<T> state, @NotNull StateValue<T> value);
}
