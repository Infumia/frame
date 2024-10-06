package net.infumia.frame.pipeline.service.viewer;

import net.infumia.frame.metadata.MetadataKeyHolder;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextViewer;
import net.infumia.frame.view.ViewHandler;
import net.infumia.frame.viewer.Viewer;
import org.jetbrains.annotations.NotNull;

public final class ServiceRemovedOnViewerRemoved
    implements PipelineServiceConsumer<PipelineContextViewer.Removed> {

    public static final PipelineServiceConsumer<PipelineContextViewer.Removed> INSTANCE =
        new ServiceRemovedOnViewerRemoved();

    public static final String KEY = "on-viewer-removed";

    @NotNull
    @Override
    public String key() {
        return ServiceRemovedOnViewerRemoved.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextViewer.Removed ctx) {
        final Object instance = ctx.context().view().instance();
        if (instance instanceof ViewHandler) {
            final ViewHandler handler = (ViewHandler) instance;
            for (final Viewer viewer : ctx.viewers()) {
                handler.onViewerRemoved(
                    viewer.metadata().getOrThrow(MetadataKeyHolder.CONTEXTUAL_VIEWER)
                );
            }
        }
    }

    private ServiceRemovedOnViewerRemoved() {}
}
