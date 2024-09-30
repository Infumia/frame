package net.infumia.inv.core.pipeline.service.render;

import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextRender;
import net.infumia.inv.api.viewer.Viewer;
import net.infumia.inv.core.metadata.MetadataKeyHolder;
import org.jetbrains.annotations.NotNull;

public final class ServiceOpenContainer
    implements PipelineServiceConsumer<PipelineContextRender.OpenContainer> {

    public static final PipelineServiceConsumer<PipelineContextRender.OpenContainer> INSTANCE =
        new ServiceOpenContainer();

    public static final String KEY = "open-container";

    @Override
    public String key() {
        return ServiceOpenContainer.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextRender.OpenContainer ctx) {
        for (final Viewer viewer : ctx.viewers()) {
            viewer
                .metadata()
                .getOrThrow(MetadataKeyHolder.CONTEXTUAL_VIEWER)
                .context()
                .container()
                .open(viewer);
        }
    }

    private ServiceOpenContainer() {}
}
