package net.infumia.frame.pipeline.service.view;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextView;
import org.jetbrains.annotations.NotNull;

public final class ServiceOpenWaitUntil
    implements PipelineServiceConsumer<PipelineContextView.Open> {

    public static final PipelineServiceConsumer<PipelineContextView.Open> INSTANCE =
        new ServiceOpenWaitUntil();

    public static final String KEY = "wait-until";

    @NotNull
    @Override
    public String key() {
        return ServiceOpenWaitUntil.KEY;
    }

    @Override
    public CompletableFuture<State> handle(@NotNull final PipelineContextView.Open ctx) {
        final CompletableFuture<?> waitUntil = ctx.context().waitUntil();
        if (waitUntil == null) {
            return CompletableFuture.completedFuture(State.CONTINUE);
        }
        return waitUntil.thenApply(__ -> State.CONTINUE);
    }

    private ServiceOpenWaitUntil() {}
}
