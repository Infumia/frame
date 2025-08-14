package net.infumia.frame.pipeline.service.element;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.context.element.ContextElementClick;
import net.infumia.frame.element.Element;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextElement;
import net.infumia.frame.service.ConsumerService;
import org.jetbrains.annotations.NotNull;

public final class ServiceClickUpdateOnClick
    implements PipelineServiceConsumer<PipelineContextElement.Click> {

    public static final PipelineServiceConsumer<PipelineContextElement.Click> INSTANCE =
        new ServiceClickUpdateOnClick();

    public static final String KEY = "update-on-click";

    @NotNull
    @Override
    public String key() {
        return ServiceClickUpdateOnClick.KEY;
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> handle(
        @NotNull final PipelineContextElement.Click ctx
    ) {
        final ContextElementClick context = ctx.context();
        final Element element = context.element();
        if (element.updateOnClick()) {
            return element.update();
        }
        return CompletableFuture.completedFuture(ConsumerService.State.CONTINUE);
    }

    private ServiceClickUpdateOnClick() {}
}
