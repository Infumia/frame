package net.infumia.frame.pipeline.service.view;

import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextView;
import org.jetbrains.annotations.NotNull;

public final class ServiceModifyContainerLogging
    implements PipelineServiceConsumer<PipelineContextView.ModifyContainer> {

    public static final PipelineServiceConsumer<PipelineContextView.ModifyContainer> INSTANCE =
        new ServiceModifyContainerLogging();

    public static final String KEY = "logging";

    @Override
    public String key() {
        return ServiceModifyContainerLogging.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextView.ModifyContainer ctx) {
        ctx.context().frame().logger().debug("View container successfully modified.");
    }

    private ServiceModifyContainerLogging() {}
}
