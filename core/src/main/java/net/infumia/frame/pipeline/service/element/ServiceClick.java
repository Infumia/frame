package net.infumia.frame.pipeline.service.element;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.element.Element;
import net.infumia.frame.element.ElementEventHandlerHolder;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextElement;
import org.jetbrains.annotations.NotNull;

public final class ServiceClick implements PipelineServiceConsumer<PipelineContextElement.Click> {

    public static final PipelineServiceConsumer<PipelineContextElement.Click> INSTANCE =
        new ServiceClick();

    public static final String KEY = "click";

    @NotNull
    @Override
    public String key() {
        return ServiceClick.KEY;
    }

    @NotNull
    @Override
    public CompletableFuture<State> handle(@NotNull final PipelineContextElement.Click ctx) {
        final Element element = ctx.context().element();
        if (element instanceof ElementEventHandlerHolder) {
            return ((ElementEventHandlerHolder) element).eventHandler()
                .handleClick(ctx)
                .thenApply(__ -> State.CONTINUE);
        } else {
            return CompletableFuture.completedFuture(State.CONTINUE);
        }
    }

    private ServiceClick() {}
}
