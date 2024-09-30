package net.infumia.inv.core.pipeline.service.view;

import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextView;
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
        ctx.context().manager().logger().debug("View container successfully modified.");
    }

    private ServiceModifyContainerLogging() {}
}
