package net.infumia.frame.pipeline.service.render;

import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextRender;
import net.infumia.frame.viewer.Viewer;
import org.jetbrains.annotations.NotNull;

public final class ServiceOpenContainer
    implements PipelineServiceConsumer<PipelineContextRender.OpenContainer> {

    public static final PipelineServiceConsumer<PipelineContextRender.OpenContainer> INSTANCE =
        new ServiceOpenContainer();

    public static final String KEY = "open-container";

    @NotNull
    @Override
    public String key() {
        return ServiceOpenContainer.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextRender.OpenContainer ctx) {
        for (final Viewer viewer : ctx.viewers()) {
            ctx.context().container().open(viewer);
        }
    }

    private ServiceOpenContainer() {}
}
