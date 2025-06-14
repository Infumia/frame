package net.infumia.frame.pipeline.service.render;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextRender;
import net.infumia.frame.service.ConsumerService;
import net.infumia.frame.view.ViewContainer;
import net.infumia.frame.viewer.Viewer;
import org.jetbrains.annotations.NotNull;

public final class ServiceOpenContainer
    implements PipelineServiceConsumer<PipelineContextRender.OpenContainer> {

    public static final PipelineServiceConsumer<PipelineContextRender.OpenContainer> INSTANCE =
        new ServiceOpenContainer();

    public static final String KEY = "open-container";

    @NotNull
    @Override
    public String key() {
        return ServiceOpenContainer.KEY;
    }

    @Override
    public CompletableFuture<ConsumerService.State> handle(
        final PipelineContextRender.OpenContainer ctx
    ) {
        final ContextRender context = ctx.context();
        final ViewContainer container = context.container();
        final Collection<Viewer> viewers = ctx.viewers();
        return context
            .frame()
            .taskFactory()
            .runAsFuture(() -> {
                for (final Viewer viewer : viewers) {
                    container.open(viewer);
                }
            })
            .thenApply(__ -> ConsumerService.State.CONTINUE);
    }

    private ServiceOpenContainer() {}
}
