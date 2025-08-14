package net.infumia.frame.pipeline.service.render;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.infumia.frame.context.view.ContextRenderRich;
import net.infumia.frame.element.Element;
import net.infumia.frame.element.ElementRich;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextRender;
import net.infumia.frame.service.ConsumerService;
import org.jetbrains.annotations.NotNull;

public final class ServiceFirstRender
    implements PipelineServiceConsumer<PipelineContextRender.FirstRender> {

    public static final PipelineServiceConsumer<PipelineContextRender.FirstRender> INSTANCE =
        new ServiceFirstRender();

    public static final String KEY = "render";

    @NotNull
    @Override
    public String key() {
        return ServiceFirstRender.KEY;
    }

    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    public CompletableFuture<ConsumerService.State> handle(
        @NotNull final PipelineContextRender.FirstRender ctx
    ) {
        final List<Element> elements = ctx.elements();
        final ContextRenderRich context = (ContextRenderRich) ctx.context();
        for (final Element element : elements) {
            context.addElement(element);
        }
        final int size = elements.size();
        final CompletableFuture<ConsumerService.State>[] futures = new CompletableFuture[size];
        for (int i = size; i > 0; i--) {
            final ElementRich element = (ElementRich) elements.get(i - 1);
            futures[size - i] = element.pipelines().executeRender(context, false);
        }
        return CompletableFuture.allOf(futures).thenApply(__ -> ConsumerService.State.CONTINUE);
    }

    private ServiceFirstRender() {}
}
