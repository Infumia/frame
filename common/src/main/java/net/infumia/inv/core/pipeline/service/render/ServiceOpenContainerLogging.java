package net.infumia.inv.core.pipeline.service.render;

import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextRender;
import org.jetbrains.annotations.NotNull;

public final class ServiceOpenContainerLogging
    implements PipelineServiceConsumer<PipelineContextRender.OpenContainer> {

    public static final PipelineServiceConsumer<PipelineContextRender.OpenContainer> INSTANCE =
        new ServiceOpenContainerLogging();

    public static final String KEY = "logging";

    @Override
    public String key() {
        return ServiceOpenContainerLogging.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextRender.OpenContainer ctx) {
        // TODO: portlek, More detailed message.
        ctx.context().manager().logger().debug("Container opened for viewers '%s'.", ctx.viewers());
    }

    private ServiceOpenContainerLogging() {}
}
