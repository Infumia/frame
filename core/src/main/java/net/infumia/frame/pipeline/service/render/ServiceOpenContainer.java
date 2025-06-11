package net.infumia.frame.pipeline.service.render;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextRender;
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
    public void accept(
        final CompletableFuture<State> future,
        final PipelineContextRender.OpenContainer ctx
    ) {
        final ContextRender context = ctx.context();
        final ViewContainer container = context.container();
        final Collection<Viewer> viewers = ctx.viewers();
        context
            .frame()
            .taskFactory()
            .run(() -> {
                for (final Viewer viewer : viewers) {
                    container.open(viewer);
                }
                future.complete(State.CONTINUE);
            });
    }

    private ServiceOpenContainer() {}
}
