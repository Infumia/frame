package net.infumia.inv.core.pipeline.service.viewer;

import net.infumia.inv.api.context.view.ContextRender;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextViewer;
import net.infumia.inv.api.viewer.Viewer;
import org.jetbrains.annotations.NotNull;

public final class ServiceRemovedLogging
    implements PipelineServiceConsumer<PipelineContextViewer.Removed> {

    public static final PipelineServiceConsumer<PipelineContextViewer.Removed> INSTANCE =
        new ServiceRemovedLogging();

    public static final String KEY = "logging";

    @Override
    public String key() {
        return ServiceRemovedLogging.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextViewer.Removed ctx) {
        final ContextRender context = ctx.context();
        for (final Viewer viewer : ctx.viewers()) {
            context
                .manager()
                .logger()
                .debug("Viewer '%s' removed from view '%s'.", viewer, context.view().instance());
        }
    }

    private ServiceRemovedLogging() {}
}
