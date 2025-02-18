package net.infumia.frame.pipeline.service.view;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import net.infumia.frame.pipeline.PipelineService;
import net.infumia.frame.pipeline.context.PipelineContextView;
import net.infumia.frame.view.View;
import net.infumia.frame.viewer.Viewer;
import net.infumia.frame.viewer.ViewerCreator;
import org.jetbrains.annotations.NotNull;

public final class ServiceCreateViewers
    implements PipelineService<PipelineContextView.CreateViewers, Collection<Viewer>> {

    public static final PipelineService<
        PipelineContextView.CreateViewers,
        Collection<Viewer>
    > INSTANCE = new ServiceCreateViewers();

    public static final String KEY = "create";

    @NotNull
    @Override
    public CompletableFuture<Collection<Viewer>> handle(
        @NotNull final PipelineContextView.CreateViewers ctx
    ) {
        final View view = ctx.view();
        final ViewerCreator viewerCreator = view.context().frame().viewerCreator();
        return CompletableFuture.completedFuture(
            ctx
                .viewers()
                .stream()
                .map(player -> viewerCreator.create(player, view))
                .collect(Collectors.toSet())
        );
    }

    @NotNull
    @Override
    public String key() {
        return ServiceCreateViewers.KEY;
    }

    private ServiceCreateViewers() {}
}
