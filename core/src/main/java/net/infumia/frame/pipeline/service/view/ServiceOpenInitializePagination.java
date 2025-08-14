package net.infumia.frame.pipeline.service.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.infumia.frame.context.ContextRich;
import net.infumia.frame.context.view.ContextOpen;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextView;
import net.infumia.frame.service.ConsumerService;
import org.jetbrains.annotations.NotNull;

public final class ServiceOpenInitializePagination
    implements PipelineServiceConsumer<PipelineContextView.Open> {

    public static final PipelineServiceConsumer<PipelineContextView.Open> INSTANCE =
        new ServiceOpenInitializePagination();

    public static final String KEY = "initialize-pagination";

    @NotNull
    @Override
    public String key() {
        return ServiceOpenInitializePagination.KEY;
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> handle(
        @NotNull final PipelineContextView.Open ctx
    ) {
        final ContextOpen context = ctx.context();
        final Collection<CompletableFuture<?>> futures = new ArrayList<>();
        for (final net.infumia.frame.state.State<
            ?
        > state : ((ContextRich) context).stateRegistry()) {
            futures.add(state.getOrThrowWait(context));
        }
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).thenApply(__ ->
            ConsumerService.State.CONTINUE
        );
    }

    private ServiceOpenInitializePagination() {}
}
