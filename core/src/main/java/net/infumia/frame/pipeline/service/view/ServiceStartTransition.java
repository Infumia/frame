package net.infumia.frame.pipeline.service.view;

import net.infumia.frame.metadata.MetadataAccess;
import net.infumia.frame.metadata.MetadataKeyHolder;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextView;
import net.infumia.frame.viewer.ContextualViewer;
import net.infumia.frame.viewer.Viewer;
import org.jetbrains.annotations.NotNull;

public final class ServiceStartTransition
    implements PipelineServiceConsumer<PipelineContextView.StartTransition> {

    public static final PipelineServiceConsumer<PipelineContextView.StartTransition> INSTANCE =
        new ServiceStartTransition();

    public static final String KEY = "transition";

    @NotNull
    @Override
    public String key() {
        return ServiceStartTransition.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextView.StartTransition ctx) {
        for (final Viewer viewer : ctx.viewers()) {
            final MetadataAccess metadata = viewer.metadata();
            final ContextualViewer oldContext = metadata.get(MetadataKeyHolder.CONTEXTUAL_VIEWER);
            if (oldContext != null) {
                metadata.setFixed(MetadataKeyHolder.TRANSITIONING_FROM, oldContext);
            }
        }
    }

    private ServiceStartTransition() {}
}
