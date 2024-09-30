package net.infumia.inv.core.pipeline.service.viewer;

import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextViewer;
import net.infumia.inv.api.viewer.Viewer;
import net.infumia.inv.core.context.view.ContextRenderRich;
import net.infumia.inv.core.viewer.ViewerRich;
import org.jetbrains.annotations.NotNull;

public final class ServiceAdded implements PipelineServiceConsumer<PipelineContextViewer.Added> {

    public static final PipelineServiceConsumer<PipelineContextViewer.Added> INSTANCE =
        new ServiceAdded();

    public static final String KEY = "added";

    @Override
    public String key() {
        return ServiceAdded.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextViewer.Added ctx) {
        final ContextRenderRich context = (ContextRenderRich) ctx.context();
        for (final Viewer viewer : ctx.viewers()) {
            context.addViewer((ViewerRich) viewer);
        }
    }

    private ServiceAdded() {}
}
