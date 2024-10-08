package net.infumia.frame.pipeline.service.viewer;

import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.metadata.MetadataKeyHolder;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextViewer;
import net.infumia.frame.viewer.ContextualViewerImpl;
import net.infumia.frame.viewer.Viewer;
import org.jetbrains.annotations.NotNull;

public final class ServiceAddedContextualViewer
    implements PipelineServiceConsumer<PipelineContextViewer.Added> {

    public static final PipelineServiceConsumer<PipelineContextViewer.Added> INSTANCE =
        new ServiceAddedContextualViewer();

    public static final String KEY = "contextual-viewer";

    @NotNull
    @Override
    public String key() {
        return ServiceAddedContextualViewer.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextViewer.Added ctx) {
        final ContextRender context = ctx.context();
        for (final Viewer viewer : ctx.viewers()) {
            viewer
                .metadata()
                .setFixed(
                    MetadataKeyHolder.CONTEXTUAL_VIEWER,
                    new ContextualViewerImpl(viewer, context)
                );
        }
    }

    private ServiceAddedContextualViewer() {}
}
