package net.infumia.inv.core.pipeline.service.render;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.infumia.inv.api.element.Element;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextRender;
import net.infumia.inv.core.context.view.ContextRenderRich;
import net.infumia.inv.core.element.ElementRich;
import org.jetbrains.annotations.NotNull;

public final class ServiceFirstRender
    implements PipelineServiceConsumer<PipelineContextRender.FirstRender> {

    public static final PipelineServiceConsumer<PipelineContextRender.FirstRender> INSTANCE =
        new ServiceFirstRender();

    public static final String KEY = "render";

    @Override
    public String key() {
        return ServiceFirstRender.KEY;
    }

    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    public CompletableFuture<State> handle(@NotNull final PipelineContextRender.FirstRender ctx) {
        final List<Element> elements = ctx.elements();
        final ContextRenderRich context = (ContextRenderRich) ctx.context();
        for (final Element element : elements) {
            context.addElement(element);
        }
        final int size = elements.size();
        final CompletableFuture<State>[] futures = new CompletableFuture[size];
        for (int i = size; i > 0; i--) {
            final ElementRich element = (ElementRich) elements.get(i - 1);
            futures[size - i] = element.pipelines().executeRender(context);
        }
        return CompletableFuture.allOf(futures).thenApply(unused -> State.CONTINUE);
    }

    private ServiceFirstRender() {}
}
