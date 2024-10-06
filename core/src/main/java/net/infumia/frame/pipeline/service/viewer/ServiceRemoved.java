package net.infumia.frame.pipeline.service.viewer;

import net.infumia.frame.context.view.ContextRenderRich;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextViewer;
import net.infumia.frame.viewer.Viewer;
import org.jetbrains.annotations.NotNull;

public final class ServiceRemoved
    implements PipelineServiceConsumer<PipelineContextViewer.Removed> {

    public static final PipelineServiceConsumer<PipelineContextViewer.Removed> INSTANCE =
        new ServiceRemoved();

    public static final String KEY = "removed";

    @NotNull
    @Override
    public String key() {
        return ServiceRemoved.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextViewer.Removed ctx) {
        final ContextRenderRich context = (ContextRenderRich) ctx.context();
        for (final Viewer viewer : ctx.viewers()) {
            context.removeViewer(viewer);
        }
    }

    private ServiceRemoved() {}
}
