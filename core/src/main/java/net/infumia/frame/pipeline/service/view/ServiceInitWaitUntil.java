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

    @Override
    public String key() {
        return ServiceInitWaitUntil.KEY;
    }

    @Override
    public void accept(
        @NotNull final CompletableFuture<State> future,
        @NotNull final PipelineContextView.Init ctx
    ) {
        final CompletableFuture<?> waitUntil = ctx.view().context().waitUntil();
        if (waitUntil == null) {
            future.complete(State.CONTINUE);
        } else {
            waitUntil.whenComplete((__, throwable) -> {
                if (throwable == null) {
                    future.complete(State.CONTINUE);
                } else {
                    future.completeExceptionally(throwable);
                }
            });
        }
    }

    private ServiceInitWaitUntil() {}
}
