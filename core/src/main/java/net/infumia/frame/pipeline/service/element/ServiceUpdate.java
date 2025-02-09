package net.infumia.frame.pipeline.service.element;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.element.Element;
import net.infumia.frame.element.ElementEventHandlerHolder;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextElement;
import org.jetbrains.annotations.NotNull;

public final class ServiceUpdate implements PipelineServiceConsumer<PipelineContextElement.Update> {

    public static final PipelineServiceConsumer<PipelineContextElement.Update> INSTANCE =
        new ServiceUpdate();

    public static final String KEY = "update";

    @NotNull
    @Override
    public String key() {
        return ServiceUpdate.KEY;
    }

    @NotNull
    @Override
    public CompletableFuture<State> handle(@NotNull final PipelineContextElement.Update ctx) {
        final Element element = ctx.context().element();
        if (element instanceof ElementEventHandlerHolder) {
            return ((ElementEventHandlerHolder) element).eventHandler()
                .handleUpdate(ctx)
                .thenApply(__ -> State.CONTINUE);
        }
        return CompletableFuture.completedFuture(State.CONTINUE);
    }

    private ServiceUpdate() {}
}
