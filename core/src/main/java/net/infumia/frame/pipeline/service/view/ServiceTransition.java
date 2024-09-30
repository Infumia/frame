package net.infumia.frame.pipeline.service.view;

import net.infumia.frame.metadata.MetadataAccess;
import net.infumia.frame.metadata.MetadataKeyHolder;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextView;
import net.infumia.frame.viewer.ContextualViewerRich;
import net.infumia.frame.viewer.Viewer;
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
