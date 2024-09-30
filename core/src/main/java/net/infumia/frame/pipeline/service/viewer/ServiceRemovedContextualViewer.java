package net.infumia.frame.pipeline.service.viewer;

import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.context.view.ContextRenderRich;
import net.infumia.frame.metadata.MetadataAccess;
import net.infumia.frame.metadata.MetadataKeyHolder;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextViewer;
import net.infumia.frame.viewer.Viewer;
import org.jetbrains.annotations.NotNull;

public final class ServiceRemovedContextualViewer
    implements PipelineServiceConsumer<PipelineContextViewer.Removed> {

    public static final PipelineServiceConsumer<PipelineContextViewer.Removed> INSTANCE =
        new ServiceRemovedContextualViewer();

    public static final String KEY = "contextual-viewer";

    @Override
    public String key() {
        return ServiceRemovedContextualViewer.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextViewer.Removed ctx) {
        final ContextRender currentContext = ctx.context();
        for (final Viewer viewer : ctx.viewers()) {
            final MetadataAccess metadata = viewer.metadata();
            final ContextRenderRich oldOrNewContext = metadata
                .getOrThrow(MetadataKeyHolder.CONTEXTUAL_VIEWER)
                .context();
            if (currentContext.id().equals(oldOrNewContext.id())) {
                metadata.remove(MetadataKeyHolder.CONTEXTUAL_VIEWER);
            }
        }
    }

    private ServiceRemovedContextualViewer() {}
}
