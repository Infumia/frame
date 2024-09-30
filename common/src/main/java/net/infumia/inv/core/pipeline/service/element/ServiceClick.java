package net.infumia.inv.core.pipeline.service.element;

import java.util.concurrent.CompletableFuture;
import net.infumia.inv.api.element.Element;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextElement;
import net.infumia.inv.core.element.ElementEventHandlerHolder;
import org.jetbrains.annotations.NotNull;

public final class ServiceClick implements PipelineServiceConsumer<PipelineContextElement.Click> {

    public static final PipelineServiceConsumer<PipelineContextElement.Click> INSTANCE =
        new ServiceClick();

    public static final String KEY = "click";

    @Override
    public String key() {
        return ServiceClick.KEY;
    }

    @NotNull
    @Override
    public CompletableFuture<State> handle(@NotNull final PipelineContextElement.Click ctx) {
        final Element element = ctx.context().element();
        if (element instanceof ElementEventHandlerHolder) {
            return ((ElementEventHandlerHolder) element).eventHandler().handleClick(ctx);
        } else {
            return CompletableFuture.completedFuture(State.CONTINUE);
        }
    }

    private ServiceClick() {}
}
