package net.infumia.frame.pipeline.service.viewer;

import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextViewer;
import net.infumia.frame.viewer.Viewer;
import org.jetbrains.annotations.NotNull;

public final class ServiceAddedLogging
    implements PipelineServiceConsumer<PipelineContextViewer.Added> {

    public static final PipelineServiceConsumer<PipelineContextViewer.Added> INSTANCE =
        new ServiceAddedLogging();

    public static final String KEY = "logging";

    @Override
    public String key() {
        return ServiceAddedLogging.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextViewer.Added ctx) {
        final ContextRender context = ctx.context();
        for (final Viewer viewer : ctx.viewers()) {
            context
                .frame()
                .logger()
                .debug("Viewer '%s' added to view '%s'.", viewer, context.view().instance());
        }
    }

    private ServiceAddedLogging() {}
}
