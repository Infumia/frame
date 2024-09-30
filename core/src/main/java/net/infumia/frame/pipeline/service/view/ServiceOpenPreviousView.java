package net.infumia.frame.pipeline.service.view;

import java.util.Deque;
import java.util.LinkedList;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.metadata.MetadataAccess;
import net.infumia.frame.metadata.MetadataKeyHolder;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextView;
import net.infumia.frame.viewer.ContextualViewer;
import net.infumia.frame.viewer.Viewer;
import org.jetbrains.annotations.NotNull;

public final class ServiceOpenPreviousView
    implements PipelineServiceConsumer<PipelineContextView.Open> {

    public static final PipelineServiceConsumer<PipelineContextView.Open> INSTANCE =
        new ServiceOpenPreviousView();

    public static final String KEY = "previous-view";

    @Override
    public String key() {
        return ServiceOpenPreviousView.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextView.Open ctx) {
        for (final Viewer viewer : ctx.context().viewers()) {
            final MetadataAccess metadata = viewer.metadata();
            final ContextualViewer oldContext = metadata.get(MetadataKeyHolder.CONTEXTUAL_VIEWER);
            if (oldContext == null) {
                continue;
            }
            Deque<ContextRender> previousViews = metadata.get(MetadataKeyHolder.PREVIOUS_VIEWS);
            if (previousViews == null) {
                previousViews = new LinkedList<>();
                metadata.setFixed(MetadataKeyHolder.PREVIOUS_VIEWS, previousViews);
            }
            previousViews.add(oldContext.context());
        }
    }

    private ServiceOpenPreviousView() {}
}
