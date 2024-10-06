package net.infumia.frame.state.value;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import net.infumia.frame.context.ContextBase;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextState;
import net.infumia.frame.pipeline.executor.PipelineExecutorState;
import net.infumia.frame.pipeline.executor.PipelineExecutorStateImpl;
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
    private final PipelineExecutorState pipelines;

    public StateValueHostImpl(@NotNull final ContextBase context) {
        this.context = context;
        this.pipelines = new PipelineExecutorStateImpl(context);
    }

    @NotNull
    @Override
    public PipelineExecutorState statePipelines() {
        return this.pipelines;
    }

    @NotNull
    @Override
    public Map<StateRich<Object>, StateValue<Object>> stateValues() {
        return Collections.unmodifiableMap(this.values);
    }

    @NotNull
    @Override
    public <T> StateValue<T> accessStateValueOrInitialize(@NotNull final StateRich<T> state) {
        return this.accessStateValueOrInitializeInternally(state, this.accessStateValue(state));
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public <T> StateValue<T> accessStateValue(@NotNull final StateRich<T> state) {
        final StateValue<Object> value = this.values.get(state);
        if (value == null) {
            this.context.frame()
                .logger()
                .debug("State '%s' not found in '%s'!", state, this.values);
            return null;
        }
        this.pipelines.executeAccess(state, value);
        return (StateValue<T>) value;
    }

    @Nullable
    @Override
    public <T> StateValue<T> updateStateValue(
        @NotNull final StateMutableRich<T> state,
        @Nullable final T value
    ) {
        final StateValue<T> stateValue = this.accessStateValue(state);
        if (stateValue == null) {
            this.context.frame().logger().debug("State '%s' is not registered!", state);
            return null;
        }
        final T oldValue = stateValue.value();
        stateValue.value(value);
        this.pipelines.executeUpdate(state, oldValue, stateValue);
        return stateValue;
    }

    @Nullable
    @Override
    public <T> StateValue<T> updateStateValue(@NotNull final StateRich<T> state) {
        final StateValue<T> stateValue = this.accessStateValue(state);
        if (stateValue == null) {
            this.context.frame().logger().debug("State '%s' is not registered!", state);
            return null;
        }
        this.pipelines.executeUpdate(state, stateValue.value(), stateValue);
        return stateValue;
    }

    @NotNull
    @Override
    public <T> CompletableFuture<StateValue<T>> accessStateValueOrInitializeWait(
        @NotNull final StateRich<T> state
    ) {
        return this.accessStateValueWait(state).thenApply(value ->
                this.accessStateValueOrInitializeInternally(state, value)
            );
    }

    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    public <T> CompletableFuture<@Nullable StateValue<T>> accessStateValueWait(
        @NotNull final StateRich<T> state
    ) {
        final StateValue<Object> value = this.values.get(state);
        if (value == null) {
            this.context.frame()
                .logger()
                .debug("State '%s' not found in '%s'!", state, this.values);
            return CompletableFuture.completedFuture(null);
        }
        return this.pipelines.executeAccess(state, value).thenApply(__ -> (StateValue<T>) value);
    }

    @NotNull
    @Override
    public <T> CompletableFuture<@Nullable StateValue<T>> updateStateValueWait(
        @NotNull final StateMutableRich<T> state,
        @Nullable final T value
    ) {
        return this.accessStateValueWait(state).thenCompose(stateValue -> {
                if (stateValue == null) {
                    this.context.frame().logger().debug("State '%s' is not registered!", state);
                    return CompletableFuture.completedFuture(null);
                }
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
                    this.context.frame().logger().debug("State '%s' is not registered!", state);
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

    @NotNull
    private <T> StateValue<T> accessStateValueOrInitializeInternally(
        @NotNull final StateRich<T> state,
        @Nullable final StateValue<T> value
    ) {
        if (value != null) {
            return value;
        }
        final StateValue<T> stateValue = state.valueFactory().apply(this.context, state);
        this.initializeState(state, stateValue);
        return stateValue;
    }
}
