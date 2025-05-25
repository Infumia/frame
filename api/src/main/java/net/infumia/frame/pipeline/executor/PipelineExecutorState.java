package net.infumia.frame.pipeline.executor;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.pipeline.context.PipelineContextState;
import net.infumia.frame.service.ConsumerService;
import net.infumia.frame.service.Implementation;
import net.infumia.frame.state.State;
import net.infumia.frame.state.value.StateValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PipelineExecutorState {
    @NotNull
    CompletableFuture<ConsumerService.State> executeAccess(
        @NotNull State<?> state,
        @NotNull StateValue<?> value
    );

    @NotNull
    CompletableFuture<ConsumerService.State> executeUpdate(
        @NotNull State<?> state,
        @Nullable Object oldValue,
        @NotNull StateValue<?> value
    );

    void applyAccess(
        @NotNull Implementation<PipelineContextState.Access, ConsumerService.State> implementation
    );

    void applyUpdate(
        @NotNull Implementation<PipelineContextState.Update, ConsumerService.State> implementation
    );
}
