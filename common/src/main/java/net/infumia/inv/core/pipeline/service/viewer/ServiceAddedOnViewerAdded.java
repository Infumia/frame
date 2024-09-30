package net.infumia.inv.core.pipeline.service.viewer;

import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextViewer;
import net.infumia.inv.api.view.ViewHandler;
import net.infumia.inv.api.viewer.Viewer;
import net.infumia.inv.core.metadata.MetadataKeyHolder;
import net.infumia.inv.core.viewer.ViewerRich;
import org.jetbrains.annotations.NotNull;

public final class ServiceAddedOnViewerAdded
    implements PipelineServiceConsumer<PipelineContextViewer.Added> {

    public static final PipelineServiceConsumer<PipelineContextViewer.Added> INSTANCE =
        new ServiceAddedOnViewerAdded();

    public static final String KEY = "on-viewer-added";

    @Override
    public String key() {
        return ServiceAddedOnViewerAdded.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextViewer.Added ctx) {
        final Object instance = ctx.context().view().instance();
        if (instance instanceof ViewHandler) {
            final ViewHandler handler = (ViewHandler) instance;
            for (final Viewer viewer : ctx.viewers()) {
                final ViewerRich rich = (ViewerRich) viewer;
                handler.onViewerAdded(
                    rich.metadata().getOrThrow(MetadataKeyHolder.CONTEXTUAL_VIEWER)
                );
            }
        }
    }

    private ServiceAddedOnViewerAdded() {}
}
