package net.infumia.inv.core.pipeline.service.element;

import java.util.concurrent.CompletableFuture;
import net.infumia.inv.api.element.Element;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextElement;
import net.infumia.inv.core.element.ElementEventHandlerHolder;
import org.jetbrains.annotations.NotNull;

public final class ServiceUpdate implements PipelineServiceConsumer<PipelineContextElement.Update> {

    public static final PipelineServiceConsumer<PipelineContextElement.Update> INSTANCE =
        new ServiceUpdate();

    public static final String KEY = "update";

    @Override
    public String key() {
        return ServiceUpdate.KEY;
    }

    @NotNull
    @Override
    public CompletableFuture<State> handle(@NotNull final PipelineContextElement.Update ctx) {
        final Element element = ctx.context().element();
        if (element instanceof ElementEventHandlerHolder) {
            return ((ElementEventHandlerHolder) element).eventHandler().handleUpdate(ctx);
        }
        return CompletableFuture.completedFuture(State.CONTINUE);
    }

    private ServiceUpdate() {}
}
