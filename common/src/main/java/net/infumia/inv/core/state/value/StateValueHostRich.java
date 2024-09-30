package net.infumia.inv.core.state.value;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import net.infumia.inv.api.pipeline.executor.PipelineExecutorState;
import net.infumia.inv.api.state.value.StateValue;
import net.infumia.inv.api.state.value.StateValueHost;
import net.infumia.inv.api.state.watcher.StateWatcherAccess;
import net.infumia.inv.api.state.watcher.StateWatcherUpdate;
import net.infumia.inv.core.state.StateMutableRich;
import net.infumia.inv.core.state.StateRich;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface StateValueHostRich extends StateValueHost {
    @NotNull
    PipelineExecutorState statePipelines();

    @NotNull
    Map<StateRich<Object>, StateValue<Object>> stateValues();

    @NotNull
    <T> StateValue<T> accessStateValueOrInitialize(@NotNull StateRich<T> state);

    @Nullable
    <T> StateValue<T> accessStateValue(@NotNull StateRich<T> state);

    @Nullable
    <T> StateValue<T> updateStateValue(@NotNull StateMutableRich<T> state, @Nullable T value);

    @Nullable
    <T> StateValue<T> updateStateValue(@NotNull StateRich<T> state);

    @NotNull
    <T> CompletableFuture<StateValue<T>> accessStateValueOrInitializeWait(
        @NotNull StateRich<T> state
    );

    @NotNull
    <T> CompletableFuture<@Nullable StateValue<T>> accessStateValueWait(
        @NotNull StateRich<T> state
    );

    @NotNull
    <T> CompletableFuture<@Nullable StateValue<T>> updateStateValueWait(
        @NotNull StateMutableRich<T> state,
        @Nullable T value
    );

    @NotNull
    <T> CompletableFuture<@Nullable StateValue<T>> updateStateValueWait(
        @NotNull StateRich<T> state
    );

    <T> void watchStateAccess(@NotNull StateRich<T> state, @NotNull StateWatcherAccess<T> watcher);

    <T> void watchStateUpdate(@NotNull StateRich<T> state, @NotNull StateWatcherUpdate<T> watcher);

    <T> void initializeState(@NotNull StateRich<T> state, @NotNull StateValue<T> value);
}
