package net.infumia.frame.pipeline.service.viewer;

import net.infumia.frame.context.view.ContextRenderRich;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextViewer;
import net.infumia.frame.viewer.Viewer;
import org.jetbrains.annotations.NotNull;

public final class ServiceAdded implements PipelineServiceConsumer<PipelineContextViewer.Added> {

    public static final PipelineServiceConsumer<PipelineContextViewer.Added> INSTANCE =
        new ServiceAdded();

    public static final String KEY = "added";

    @NotNull
    @Override
    public String key() {
        return ServiceAdded.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextViewer.Added ctx) {
        final ContextRenderRich context = (ContextRenderRich) ctx.context();
        for (final Viewer viewer : ctx.viewers()) {
            context.addViewer(viewer);
        }
    }

    private ServiceAdded() {}
}
