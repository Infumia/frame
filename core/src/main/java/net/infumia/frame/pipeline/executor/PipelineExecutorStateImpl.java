package net.infumia.frame.pipeline.executor;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.context.ContextBase;
import net.infumia.frame.pipeline.context.PipelineContextState;
import net.infumia.frame.pipeline.context.PipelineContextStates;
import net.infumia.frame.pipeline.holder.PipelineHolderState;
import net.infumia.frame.service.ConsumerService;
import net.infumia.frame.service.Implementation;
import net.infumia.frame.state.State;
import net.infumia.frame.state.StateRich;
import net.infumia.frame.state.value.StateValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class PipelineExecutorStateImpl implements PipelineExecutorState {

    private final PipelineHolderState pipelines = PipelineHolderState.BASE.createNew();
    private final ContextBase context;

    public PipelineExecutorStateImpl(@NotNull final ContextBase context) {
        this.context = context;
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> executeAccess(
        @NotNull final State<?> state,
        @NotNull final StateValue<?> value
    ) {
        return this.pipelines.access()
            .completeWith(
                new PipelineContextStates.Access(
                    this.context.manager(),
                    (StateRich<?>) state,
                    value
                )
            );
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> executeUpdate(
        @NotNull final State<?> state,
        @Nullable final Object oldValue,
        @NotNull final StateValue<?> value
    ) {
        return this.pipelines.update()
            .completeWith(
                new PipelineContextStates.Update(
                    this.context.manager(),
                    (StateRich<?>) state,
                    oldValue,
                    value
                )
            );
    }

    @Override
    public void applyAccess(
        @NotNull final Implementation<
            PipelineContextState.Access,
            ConsumerService.State
        > implementation
    ) {
        this.pipelines.access().apply(implementation);
    }

    @Override
    public void applyUpdate(
        @NotNull final Implementation<
            PipelineContextState.Update,
            ConsumerService.State
        > implementation
    ) {
        this.pipelines.update().apply(implementation);
    }
}
