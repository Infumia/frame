package net.infumia.inv.core.pipeline.service.view;

import net.infumia.inv.api.context.ContextBase;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextView;
import net.infumia.inv.api.viewer.Viewer;
import net.infumia.inv.core.metadata.MetadataKeyHolder;
import net.infumia.inv.core.viewer.ContextualViewerRich;
import org.jetbrains.annotations.NotNull;

public final class ServiceTransitionLogging
    implements PipelineServiceConsumer<PipelineContextView.Transition> {

    public static final PipelineServiceConsumer<PipelineContextView.Transition> INSTANCE =
        new ServiceTransitionLogging();

    public static final String KEY = "logging";

    @Override
    public String key() {
        return ServiceTransitionLogging.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextView.Transition ctx) {
        // TODO: portlek, More detailed message.
        final ContextBase context = ctx.context();
        for (final Viewer viewer : ctx.viewers()) {
            final ContextualViewerRich transitioningFrom = viewer
                .metadata()
                .get(MetadataKeyHolder.TRANSITIONING_FROM);
            if (transitioningFrom == null) {
                continue;
            }
            context
                .manager()
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
