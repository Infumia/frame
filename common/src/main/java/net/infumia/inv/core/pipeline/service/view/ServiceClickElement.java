package net.infumia.inv.core.pipeline.service.view;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import net.infumia.inv.api.context.view.ContextClick;
import net.infumia.inv.api.element.Element;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.Pipelined;
import net.infumia.inv.api.pipeline.context.PipelineContextView;
import net.infumia.inv.core.element.ElementRich;
import org.jetbrains.annotations.NotNull;

public final class ServiceClickElement
    implements PipelineServiceConsumer<PipelineContextView.Click> {

    public static final PipelineServiceConsumer<PipelineContextView.Click> INSTANCE =
        new ServiceClickElement();

    public static final String KEY = "element";

    @Override
    public String key() {
        return ServiceClickElement.KEY;
    }

    @NotNull
    @Override
    public CompletableFuture<State> handle(@NotNull final PipelineContextView.Click ctx) {
        final ContextClick context = ctx.context();
        final int clickedSlot = context.clickedSlotRaw();
        final List<Element> elements = context.elements();
        final Optional<ElementRich> found = elements
            .stream()
            .map(ElementRich.class::cast)
            .filter(e -> e.containedWithin(clickedSlot))
            .findFirst();
        return found
            .map(Pipelined::pipelines)
            .map(pipelines -> pipelines.executeClick(context))
            .orElseGet(() -> CompletableFuture.completedFuture(State.CONTINUE));
    }

    private ServiceClickElement() {}
}
