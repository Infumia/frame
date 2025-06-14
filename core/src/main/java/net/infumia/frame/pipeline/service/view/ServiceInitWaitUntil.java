package net.infumia.frame.pipeline.service.view;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextView;
import org.jetbrains.annotations.NotNull;

public final class ServiceInitWaitUntil
    implements PipelineServiceConsumer<PipelineContextView.Init> {

    public static final PipelineServiceConsumer<PipelineContextView.Init> INSTANCE =
        new ServiceInitWaitUntil();

    public static final String KEY = "wait-until";

    @NotNull
    @Override
    public String key() {
        return ServiceInitWaitUntil.KEY;
    }

    @Override
    public CompletableFuture<State> handle(@NotNull final PipelineContextView.Init ctx) {
        final CompletableFuture<?> waitUntil = ctx.view().context().waitUntil();
        if (waitUntil == null) {
            return CompletableFuture.completedFuture(State.CONTINUE);
        }
        return waitUntil.thenApply(__ -> State.CONTINUE);
    }

    private ServiceInitWaitUntil() {}
}
