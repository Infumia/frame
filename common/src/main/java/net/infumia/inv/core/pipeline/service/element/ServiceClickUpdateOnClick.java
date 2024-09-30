package net.infumia.inv.core.pipeline.service.element;

import java.util.concurrent.CompletableFuture;
import net.infumia.inv.api.context.element.ContextElementClick;
import net.infumia.inv.api.element.Element;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextElement;
import net.infumia.inv.core.element.ElementRich;
import org.jetbrains.annotations.NotNull;

public final class ServiceClickUpdateOnClick
    implements PipelineServiceConsumer<PipelineContextElement.Click> {

    public static final PipelineServiceConsumer<PipelineContextElement.Click> INSTANCE =
        new ServiceClickUpdateOnClick();

    public static final String KEY = "update-on-click";

    @Override
    public String key() {
        return ServiceClickUpdateOnClick.KEY;
    }

    @NotNull
    @Override
    public CompletableFuture<State> handle(@NotNull final PipelineContextElement.Click ctx) {
        final ContextElementClick context = ctx.context();
        final Element element = context.element();
        if (element.updateOnClick()) {
            return ((ElementRich) element).pipelines().executeUpdate(context, false);
        }
        return CompletableFuture.completedFuture(State.CONTINUE);
    }

    private ServiceClickUpdateOnClick() {}
}
