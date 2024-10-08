package net.infumia.frame.pipeline.service.viewer;

import net.infumia.frame.metadata.MetadataKeyHolder;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextViewer;
import net.infumia.frame.view.ViewHandler;
import net.infumia.frame.viewer.Viewer;
import org.jetbrains.annotations.NotNull;

public final class ServiceAddedOnViewerAdded
    implements PipelineServiceConsumer<PipelineContextViewer.Added> {

    public static final PipelineServiceConsumer<PipelineContextViewer.Added> INSTANCE =
        new ServiceAddedOnViewerAdded();

    public static final String KEY = "on-viewer-added";

    @NotNull
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
                handler.onViewerAdded(
                    viewer.metadata().getOrThrow(MetadataKeyHolder.CONTEXTUAL_VIEWER)
                );
            }
        }
    }

    private ServiceAddedOnViewerAdded() {}
}
