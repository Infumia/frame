package net.infumia.inv.core.pipeline.service.viewer;

import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextViewer;
import net.infumia.inv.api.viewer.Viewer;
import net.infumia.inv.core.context.view.ContextRenderRich;
import net.infumia.inv.core.viewer.ViewerRich;
import org.jetbrains.annotations.NotNull;

public final class ServiceRemoved
    implements PipelineServiceConsumer<PipelineContextViewer.Removed> {

    public static final PipelineServiceConsumer<PipelineContextViewer.Removed> INSTANCE =
        new ServiceRemoved();

    public static final String KEY = "removed";

    @Override
    public String key() {
        return ServiceRemoved.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextViewer.Removed ctx) {
        final ContextRenderRich context = (ContextRenderRich) ctx.context();
        for (final Viewer viewer : ctx.viewers()) {
            context.removeViewer((ViewerRich) viewer);
        }
    }

    private ServiceRemoved() {}
}
