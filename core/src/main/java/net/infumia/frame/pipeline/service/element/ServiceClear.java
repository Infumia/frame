package net.infumia.frame.pipeline.service.element;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.element.Element;
import net.infumia.frame.element.ElementEventHandlerHolder;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextElement;
import org.jetbrains.annotations.NotNull;

public final class ServiceClear implements PipelineServiceConsumer<PipelineContextElement.Clear> {

    public static final PipelineServiceConsumer<PipelineContextElement.Clear> INSTANCE =
        new ServiceClear();

    public static final String KEY = "clear";

    @NotNull
    @Override
    public String key() {
        return ServiceClear.KEY;
    }

    @NotNull
    @Override
    public CompletableFuture<State> handle(@NotNull final PipelineContextElement.Clear ctx) {
        final Element element = ctx.context().element();
        if (element instanceof ElementEventHandlerHolder) {
            return ((ElementEventHandlerHolder) element).eventHandler()
                .handleClear(ctx)
                .thenApply(__ -> State.CONTINUE);
        } else {
            return CompletableFuture.completedFuture(State.CONTINUE);
        }
    }

    private ServiceClear() {}
}
