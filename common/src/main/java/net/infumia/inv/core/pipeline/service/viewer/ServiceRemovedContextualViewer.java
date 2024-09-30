package net.infumia.inv.core.pipeline.service.viewer;

import net.infumia.inv.api.context.view.ContextRender;
import net.infumia.inv.api.metadata.MetadataAccess;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextViewer;
import net.infumia.inv.api.viewer.Viewer;
import net.infumia.inv.core.context.view.ContextRenderRich;
import net.infumia.inv.core.metadata.MetadataKeyHolder;
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
