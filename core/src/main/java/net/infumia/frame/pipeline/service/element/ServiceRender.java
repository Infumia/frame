package net.infumia.frame.pipeline.service.element;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.element.Element;
import net.infumia.frame.element.ElementEventHandlerHolder;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextElement;
import net.infumia.frame.service.ConsumerService;
import org.jetbrains.annotations.NotNull;

public final class ServiceRender implements PipelineServiceConsumer<PipelineContextElement.Render> {

    public static final PipelineServiceConsumer<PipelineContextElement.Render> INSTANCE =
        new ServiceRender();

    public static final String KEY = "render";

    @NotNull
    @Override
    public String key() {
        return ServiceRender.KEY;
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> handle(
        @NotNull final PipelineContextElement.Render ctx
    ) {
        final Element element = ctx.context().element();
        if (element instanceof ElementEventHandlerHolder) {
            return ((ElementEventHandlerHolder) element).eventHandler()
                .handleRender(ctx)
                .thenApply(__ -> ConsumerService.State.CONTINUE);
        }
        return CompletableFuture.completedFuture(ConsumerService.State.CONTINUE);
    }

    private ServiceRender() {}
}
