package net.infumia.inv.core.pipeline.service.view;

import net.infumia.inv.api.metadata.MetadataAccess;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextView;
import net.infumia.inv.api.viewer.Viewer;
import net.infumia.inv.core.metadata.MetadataKeyHolder;
import net.infumia.inv.core.viewer.ContextualViewerRich;
import org.jetbrains.annotations.NotNull;

public final class ServiceTransition
    implements PipelineServiceConsumer<PipelineContextView.Transition> {

    public static final PipelineServiceConsumer<PipelineContextView.Transition> INSTANCE =
        new ServiceTransition();

    public static final String KEY = "transition";

    @Override
    public String key() {
        return ServiceTransition.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextView.Transition ctx) {
        for (final Viewer viewer : ctx.viewers()) {
            final MetadataAccess metadata = viewer.metadata();
            final ContextualViewerRich oldContext = metadata.get(
                MetadataKeyHolder.CONTEXTUAL_VIEWER
            );
            if (oldContext != null) {
                metadata.setFixed(MetadataKeyHolder.TRANSITIONING_FROM, oldContext);
            }
        }
    }

    private ServiceTransition() {}
}
