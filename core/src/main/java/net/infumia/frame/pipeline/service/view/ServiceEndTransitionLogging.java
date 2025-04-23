package net.infumia.frame.pipeline.service.view;

import net.infumia.frame.context.ContextBase;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextView;
import net.infumia.frame.viewer.Viewer;
import org.jetbrains.annotations.NotNull;

public final class ServiceEndTransitionLogging
    implements PipelineServiceConsumer<PipelineContextView.EndTransition> {

    public static final PipelineServiceConsumer<PipelineContextView.EndTransition> INSTANCE =
        new ServiceEndTransitionLogging();

    public static final String KEY = "logging";

    @NotNull
    @Override
    public String key() {
        return ServiceEndTransitionLogging.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextView.EndTransition ctx) {
        // TODO: portlek, More detailed message.
        final ContextBase context = ctx.context();
        for (final Viewer viewer : ctx.viewers()) {
            context.frame().logger().debug("Ending transition for player '%s'.", viewer);
        }
    }

    private ServiceEndTransitionLogging() {}
}
