package net.infumia.frame.pipeline.service.view;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextView;
import net.infumia.frame.service.ConsumerService;
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
    public void accept(
        @NotNull final CompletableFuture<ConsumerService.State> future,
        @NotNull final PipelineContextView.Open ctx
    ) {
        final CompletableFuture<?> waitUntil = ctx.context().waitUntil();
        if (waitUntil == null) {
            future.complete(ConsumerService.State.CONTINUE);
        } else {
            waitUntil.whenComplete((result, throwable) -> {
                if (throwable == null) {
                    future.complete(ConsumerService.State.CONTINUE);
                } else {
                    future.completeExceptionally(throwable);
                }
            });
        }
    }

    private ServiceOpenWaitUntil() {}
}
