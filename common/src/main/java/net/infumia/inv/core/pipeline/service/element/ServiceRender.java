package net.infumia.inv.core.pipeline.service.element;

import java.util.concurrent.CompletableFuture;
import net.infumia.inv.api.element.Element;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextElement;
import net.infumia.inv.core.element.ElementEventHandlerHolder;
import org.jetbrains.annotations.NotNull;

public final class ServiceRender implements PipelineServiceConsumer<PipelineContextElement.Render> {

    public static final PipelineServiceConsumer<PipelineContextElement.Render> INSTANCE =
        new ServiceRender();

    public static final String KEY = "render";

    @Override
    public String key() {
        return ServiceRender.KEY;
    }

    @NotNull
    @Override
    public CompletableFuture<State> handle(@NotNull final PipelineContextElement.Render ctx) {
        final Element element = ctx.context().element();
        if (element instanceof ElementEventHandlerHolder) {
            return ((ElementEventHandlerHolder) element).eventHandler().handleRender(ctx);
        }
        return CompletableFuture.completedFuture(State.CONTINUE);
    }

    private ServiceRender() {}
}
