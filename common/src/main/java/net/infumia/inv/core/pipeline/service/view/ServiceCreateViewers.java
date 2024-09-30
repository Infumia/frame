package net.infumia.inv.core.pipeline.service.view;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import net.infumia.inv.api.pipeline.PipelineService;
import net.infumia.inv.api.pipeline.context.PipelineContextView;
import net.infumia.inv.api.view.View;
import net.infumia.inv.api.viewer.Viewer;
import net.infumia.inv.api.viewer.ViewerCreator;
import org.jetbrains.annotations.NotNull;

public final class ServiceCreateViewers
    implements PipelineService<PipelineContextView.CreateViewers, Collection<Viewer>> {

    public static final PipelineService<
        PipelineContextView.CreateViewers,
        Collection<Viewer>
    > INSTANCE = new ServiceCreateViewers();

    public static final String KEY = "create";

    @Override
    public String key() {
        return ServiceCreateViewers.KEY;
    }

    @NotNull
    @Override
    public CompletableFuture<Collection<Viewer>> handle(
        @NotNull final PipelineContextView.CreateViewers ctx
    ) {
        final View view = ctx.view();
        final ViewerCreator viewerCreator = view.context().manager().viewerCreator();
        return CompletableFuture.completedFuture(
            ctx
                .viewers()
                .stream()
                .map(player -> viewerCreator.create(player, view))
                .collect(Collectors.toSet())
        );
    }

    private ServiceCreateViewers() {}
}
