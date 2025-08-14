package net.infumia.frame.pipeline.service.view;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.context.view.ContextClick;
import net.infumia.frame.element.ElementRich;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.Pipelined;
import net.infumia.frame.pipeline.context.PipelineContextView;
import net.infumia.frame.service.ConsumerService;
import org.jetbrains.annotations.NotNull;

public final class ServiceClickElement
    implements PipelineServiceConsumer<PipelineContextView.Click> {

    public static final PipelineServiceConsumer<PipelineContextView.Click> INSTANCE =
        new ServiceClickElement();

    public static final String KEY = "element";

    @NotNull
    @Override
    public String key() {
        return ServiceClickElement.KEY;
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> handle(
        @NotNull final PipelineContextView.Click ctx
    ) {
        final ContextClick context = ctx.context();
        final int clickedSlot = context.clickedSlotRaw();
        return context
            .elements()
            .stream()
            .map(ElementRich.class::cast)
            .filter(ElementRich::visible)
            .filter(e -> e.containedWithin(clickedSlot))
            .findFirst()
            .map(Pipelined::pipelines)
            .map(pipelines -> pipelines.executeClick(context))
            .orElseGet(() -> CompletableFuture.completedFuture(ConsumerService.State.CONTINUE));
    }

    private ServiceClickElement() {}
}
