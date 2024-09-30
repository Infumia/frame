package net.infumia.inv.core.pipeline.service.view;

import java.util.Deque;
import java.util.LinkedList;
import net.infumia.inv.api.metadata.MetadataAccess;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextView;
import net.infumia.inv.api.viewer.Viewer;
import net.infumia.inv.core.context.view.ContextRenderRich;
import net.infumia.inv.core.metadata.MetadataKeyHolder;
import net.infumia.inv.core.viewer.ContextualViewerRich;
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
            final ContextualViewerRich oldContext = metadata.get(
                MetadataKeyHolder.CONTEXTUAL_VIEWER
            );
            if (oldContext != null) {
                Deque<ContextRenderRich> previousViews = metadata.get(
                    MetadataKeyHolder.PREVIOUS_VIEWS
                );
                if (previousViews == null) {
                    previousViews = new LinkedList<>();
                    metadata.setFixed(MetadataKeyHolder.PREVIOUS_VIEWS, previousViews);
                }
                previousViews.add(oldContext.context());
            }
        }
    }

    private ServiceOpenPreviousView() {}
}
