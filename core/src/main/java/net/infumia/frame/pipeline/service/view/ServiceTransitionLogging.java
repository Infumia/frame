package net.infumia.frame.pipeline.service.view;

import net.infumia.frame.context.ContextBase;
import net.infumia.frame.metadata.MetadataKeyHolder;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextView;
import net.infumia.frame.viewer.ContextualViewer;
import net.infumia.frame.viewer.Viewer;
import org.jetbrains.annotations.NotNull;

public final class ServiceTransitionLogging
    implements PipelineServiceConsumer<PipelineContextView.Transition> {

    public static final PipelineServiceConsumer<PipelineContextView.Transition> INSTANCE =
        new ServiceTransitionLogging();

    public static final String KEY = "logging";

    @NotNull
    @Override
    public String key() {
        return ServiceTransitionLogging.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextView.Transition ctx) {
        // TODO: portlek, More detailed message.
        final ContextBase context = ctx.context();
        for (final Viewer viewer : ctx.viewers()) {
            final ContextualViewer transitioningFrom = viewer
                .metadata()
                .get(MetadataKeyHolder.TRANSITIONING_FROM);
            if (transitioningFrom == null) {
                continue;
            }
            context
                .frame()
                .logger()
                .debug(
                    "Player '%s' is transitioning from view '%s' to view '%s'.",
                    viewer,
                    transitioningFrom.view().instance(),
                    context.view().instance()
                );
        }
    }

    private ServiceTransitionLogging() {}
}
