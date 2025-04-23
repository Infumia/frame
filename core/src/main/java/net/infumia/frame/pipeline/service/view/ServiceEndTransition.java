package net.infumia.frame.pipeline.service.view;

import net.infumia.frame.metadata.MetadataKeyHolder;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextView;
import net.infumia.frame.viewer.Viewer;
import org.jetbrains.annotations.NotNull;

public final class ServiceEndTransition
    implements PipelineServiceConsumer<PipelineContextView.EndTransition> {

    public static final PipelineServiceConsumer<PipelineContextView.EndTransition> INSTANCE =
        new ServiceEndTransition();

    public static final String KEY = "end-transition";

    @NotNull
    @Override
    public String key() {
        return ServiceEndTransition.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextView.EndTransition ctx) {
        for (final Viewer viewer : ctx.viewers()) {
            viewer.metadata().remove(MetadataKeyHolder.TRANSITIONING);
        }
    }

    private ServiceEndTransition() {}
}
