package net.infumia.inv.core.pipeline.service.viewer;

import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextViewer;
import net.infumia.inv.api.viewer.Viewer;
import net.infumia.inv.core.context.view.ContextRenderRich;
import net.infumia.inv.core.metadata.MetadataKeyHolder;
import net.infumia.inv.core.viewer.ContextualViewerImpl;
import net.infumia.inv.core.viewer.ViewerRich;
import org.jetbrains.annotations.NotNull;

public final class ServiceAddedContextualViewer
    implements PipelineServiceConsumer<PipelineContextViewer.Added> {

    public static final PipelineServiceConsumer<PipelineContextViewer.Added> INSTANCE =
        new ServiceAddedContextualViewer();

    public static final String KEY = "contextual-viewer";

    @Override
    public String key() {
        return ServiceAddedContextualViewer.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextViewer.Added ctx) {
        final ContextRenderRich context = (ContextRenderRich) ctx.context();
        for (final Viewer viewer : ctx.viewers()) {
            final ViewerRich rich = (ViewerRich) viewer;
            rich
                .metadata()
                .setFixed(
                    MetadataKeyHolder.CONTEXTUAL_VIEWER,
                    new ContextualViewerImpl(rich, context)
                );
        }
    }

    private ServiceAddedContextualViewer() {}
}
