package net.infumia.inv.core.pipeline.service.element;

import java.util.concurrent.CompletableFuture;
import net.infumia.inv.api.element.Element;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextElement;
import net.infumia.inv.core.element.ElementEventHandlerHolder;
import org.jetbrains.annotations.NotNull;

public final class ServiceClear implements PipelineServiceConsumer<PipelineContextElement.Clear> {

    public static final PipelineServiceConsumer<PipelineContextElement.Clear> INSTANCE =
        new ServiceClear();

    public static final String KEY = "clear";

    @Override
    public String key() {
        return ServiceClear.KEY;
    }

    @NotNull
    @Override
    public CompletableFuture<State> handle(@NotNull final PipelineContextElement.Clear ctx) {
        final Element element = ctx.context().element();
        if (element instanceof ElementEventHandlerHolder) {
            return ((ElementEventHandlerHolder) element).eventHandler().handleClear(ctx);
        } else {
            return CompletableFuture.completedFuture(State.CONTINUE);
        }
    }

    private ServiceClear() {}
}
