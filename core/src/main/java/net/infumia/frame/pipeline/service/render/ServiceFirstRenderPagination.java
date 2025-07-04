package net.infumia.frame.pipeline.service.render;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.infumia.frame.context.view.ContextRenderRich;
import net.infumia.frame.element.pagination.ElementPagination;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextRender;
import net.infumia.frame.state.pagination.StatePagination;
import org.jetbrains.annotations.NotNull;

public final class ServiceFirstRenderPagination
    implements PipelineServiceConsumer<PipelineContextRender.FirstRender> {

    public static final PipelineServiceConsumer<PipelineContextRender.FirstRender> INSTANCE =
        new ServiceFirstRenderPagination();

    public static final String KEY = "pagination";

    @NotNull
    @Override
    public String key() {
        return ServiceFirstRenderPagination.KEY;
    }

    @NotNull
    @Override
    public CompletableFuture<State> handle(@NotNull final PipelineContextRender.FirstRender ctx) {
        final ContextRenderRich context = (ContextRenderRich) ctx.context();
        final Collection<CompletableFuture<ElementPagination>> futures = new ArrayList<>();
        for (final net.infumia.frame.state.State<?> state : context.stateRegistry()) {
            if (state instanceof StatePagination) {
                futures.add(((StatePagination) state).getOrThrowWait(context));
            }
        }
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
            .thenRun(() -> futures.stream().map(CompletableFuture::join).forEach(ctx::addElement))
            .thenApply(__ -> State.CONTINUE);
    }

    private ServiceFirstRenderPagination() {}
}
