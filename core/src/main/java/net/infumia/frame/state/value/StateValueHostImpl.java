package net.infumia.frame.state.value;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import net.infumia.frame.Preconditions;
import net.infumia.frame.context.ContextBase;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextState;
import net.infumia.frame.pipeline.executor.PipelinesState;
import net.infumia.frame.pipeline.executor.PipelinesStateImpl;
import net.infumia.frame.service.Implementation;
import net.infumia.frame.state.StateMutableRich;
import net.infumia.frame.state.StateRich;
import net.infumia.frame.state.watcher.StateWatcherAccess;
import net.infumia.frame.state.watcher.StateWatcherUpdate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class StateValueHostImpl implements StateValueHostRich {

    private final Map<StateRich<Object>, StateValue<Object>> values = new HashMap<>();
    private final ContextBase context;
    private final PipelinesState pipelines;

    public StateValueHostImpl(@NotNull final ContextBase context) {
        this.context = context;
        this.pipelines = new PipelinesStateImpl(context);
    }

    @NotNull
    @Override
    public Map<StateRich<Object>, StateValue<Object>> stateValues() {
        return Collections.unmodifiableMap(this.values);
    }

    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    public <T> StateValue<T> accessStateValue(@NotNull final StateRich<T> state) {
        final StateValue<Object> value = Preconditions.stateNotNull(
            this.values.get(state),
            "State value for '%s' not found in '%s'!",
            state,
            this.values
        );
        this.context.frame()
            .loggedFuture(
                this.pipelines.executeAccess(state, value),
                "An error occurred while accessing state '%s'!",
                state
            );
        return (StateValue<T>) value;
    }

    @NotNull
    @Override
    public <T> StateValue<T> updateStateValue(
        @NotNull final StateMutableRich<T> state,
        @Nullable final T value
    ) {
        final StateValue<T> stateValue = this.accessStateValue(state);
        final T oldValue = stateValue.value();
        stateValue.value(value);
        this.context.frame()
            .loggedFuture(
                this.pipelines.executeUpdate(state, oldValue, stateValue),
                "An error occurred while updating state '%s'!",
                state
            );
        return stateValue;
    }

    @NotNull
    @Override
    public <T> StateValue<T> updateStateValue(@NotNull final StateRich<T> state) {
        final StateValue<T> stateValue = this.accessStateValue(state);
        this.context.frame()
            .loggedFuture(
                this.pipelines.executeUpdate(state, stateValue.value(), stateValue),
                "An error occurred while updating state '%s'!",
                state
            );
        return stateValue;
    }

    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    public <T> CompletableFuture<@NotNull StateValue<T>> accessStateValueWait(
        @NotNull final StateRich<T> state
    ) {
        final StateValue<Object> value = Preconditions.stateNotNull(
            this.values.get(state),
            "State value for '%s' not found in '%s'!",
            state,
            this.values
        );
        return this.pipelines.executeAccess(state, value).thenApply(__ -> (StateValue<T>) value);
    }

    @NotNull
    @Override
    public <T> CompletableFuture<@Nullable StateValue<T>> updateStateValueWait(
        @NotNull final StateMutableRich<T> state,
        @Nullable final T value
    ) {
        return this.accessStateValueWait(state).thenCompose(stateValue -> {
                final T oldValue = stateValue.value();
                stateValue.value(value);
                return this.pipelines.executeUpdate(state, oldValue, stateValue).thenApply(__ ->
                        stateValue
                    );
            });
    }

    @NotNull
    @Override
    public <T> CompletableFuture<@Nullable StateValue<T>> updateStateValueWait(
        @NotNull final StateRich<T> state
    ) {
        return this.accessStateValueWait(state).thenCompose(stateValue -> {
                if (stateValue == null) {
                    return CompletableFuture.completedFuture(null);
                }
                return this.pipelines.executeUpdate(
                        state,
                        stateValue.value(),
                        stateValue
                    ).thenApply(__ -> stateValue);
            });
    }

    @Override
    public <T> void watchStateAccess(
        @NotNull final StateRich<T> state,
        @NotNull final StateWatcherAccess<T> watcher
    ) {
        this.pipelines.applyAccess(
                Implementation.register(
                    new PipelineServiceConsumer<PipelineContextState.Access>() {
                        @NotNull
                        @Override
                        public String key() {
                            return "";
                        }

                        @Override
                        @SuppressWarnings("unchecked")
                        public void accept(@NotNull final PipelineContextState.Access ctx) {
                            if (((StateRich<?>) ctx.state()).id() == state.id()) {
                                watcher.access((StateValue<T>) ctx.value());
                            }
                        }
                    }
                )
            );
    }

    @Override
    public <T> void watchStateUpdate(
        @NotNull final StateRich<T> state,
        @NotNull final StateWatcherUpdate<T> watcher
    ) {
        this.pipelines.applyUpdate(
                Implementation.register(
                    new PipelineServiceConsumer<PipelineContextState.Update>() {
                        @NotNull
                        @Override
                        public String key() {
                            return "";
                        }

                        @Override
                        @SuppressWarnings("unchecked")
                        public void accept(@NotNull final PipelineContextState.Update ctx) {
                            if (((StateRich<?>) ctx.state()).id() == state.id()) {
                                watcher.update(
                                    new StateUpdate<>(
                                        state,
                                        (T) ctx.oldValue(),
                                        (StateValue<T>) ctx.value()
                                    )
                                );
                            }
                        }
                    }
                )
            );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> void initializeState(
        @NotNull final StateRich<T> state,
        @NotNull final StateValue<T> value
    ) {
        this.values.put((StateRich<Object>) state, (StateValue<Object>) value);
        this.context.frame().logger().debug("State '%s' initialized with value '%s'", state, value);
    }
}
