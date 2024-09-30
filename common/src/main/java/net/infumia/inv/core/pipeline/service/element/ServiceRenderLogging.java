package net.infumia.inv.core.pipeline.service.element;

import net.infumia.inv.api.context.element.ContextElementRender;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextElement;
import net.infumia.inv.core.element.ElementRich;
import org.jetbrains.annotations.NotNull;

public final class ServiceRenderLogging
    implements PipelineServiceConsumer<PipelineContextElement.Render> {

    public static final PipelineServiceConsumer<PipelineContextElement.Render> INSTANCE =
        new ServiceRenderLogging();

    public static final String KEY = "logging";

    @Override
    public String key() {
        return ServiceRenderLogging.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextElement.Render ctx) {
        final ContextElementRender context = ctx.context();
        final String key = ((ElementRich) context.element()).key();
        // TODO: portlek, Add more detailed message.
        context.manager().logger().debug("Element '%s' is rendered.", key == null ? "null" : key);
    }

    private ServiceRenderLogging() {}
}
