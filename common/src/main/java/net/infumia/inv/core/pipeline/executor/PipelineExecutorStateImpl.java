package net.infumia.inv.core.pipeline.executor;

import java.util.concurrent.CompletableFuture;
import net.infumia.inv.api.pipeline.context.PipelineContextState;
import net.infumia.inv.api.pipeline.executor.PipelineExecutorState;
import net.infumia.inv.api.service.ConsumerService;
import net.infumia.inv.api.service.Implementation;
import net.infumia.inv.api.state.State;
import net.infumia.inv.api.state.value.StateValue;
import net.infumia.inv.core.context.ContextBaseRich;
import net.infumia.inv.core.pipeline.context.PipelineContextStates;
import net.infumia.inv.core.pipeline.holder.PipelineHolderState;
import net.infumia.inv.core.state.StateRich;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class PipelineExecutorStateImpl implements PipelineExecutorState {

    private final PipelineHolderState pipelines = PipelineHolderState.BASE.createNew();
    private final ContextBaseRich context;

    public PipelineExecutorStateImpl(@NotNull final ContextBaseRich context) {
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
