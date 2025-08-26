package net.infumia.frame.pipeline.service.view;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.context.ContextBaseRich;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextView;
import net.infumia.frame.service.ConsumerService;
import net.infumia.frame.state.StateRegistry;
import net.infumia.frame.state.StateRich;
import net.infumia.frame.state.value.StateValueHostRich;
import org.jetbrains.annotations.NotNull;

public final class ServiceOpenInitializeState
    implements PipelineServiceConsumer<PipelineContextView.Open> {

    public static final PipelineServiceConsumer<PipelineContextView.Open> INSTANCE =
        new ServiceOpenInitializeState();

    public static final String KEY = "initialize-state";

    @NotNull
    @Override
    public String key() {
        return ServiceOpenInitializeState.KEY;
    }

    @Override
    public CompletableFuture<ConsumerService.State> handle(
        @NotNull final PipelineContextView.Open ctx
    ) {
        final ContextBaseRich context = (ContextBaseRich) ctx.context();
        final StateValueHostRich host = (StateValueHostRich) context.stateValueHost();
        final StateRegistry registry = context.stateRegistry();
        final CompletableFuture<?>[] futures = new CompletableFuture<?>[registry.size()];
        int index = 0;
        for (final StateRich<Object> state : registry) {
            futures[index++] = state
                .valueFactory()
                .apply(context, state)
                .thenAccept(value -> host.initializeState(state, value));
        }
        return CompletableFuture.allOf(futures).thenApply(__ -> ConsumerService.State.CONTINUE);
    }

    private ServiceOpenInitializeState() {}
}
