package net.infumia.inv.core.pipeline.service.element;

import net.infumia.inv.api.context.element.ContextElementUpdate;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextElement;
import net.infumia.inv.core.element.ElementRich;
import org.jetbrains.annotations.NotNull;

public final class ServiceUpdateLogging
    implements PipelineServiceConsumer<PipelineContextElement.Update> {

    public static final PipelineServiceConsumer<PipelineContextElement.Update> INSTANCE =
        new ServiceUpdateLogging();

    public static final String KEY = "logging";

    @Override
    public String key() {
        return ServiceUpdateLogging.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextElement.Update ctx) {
        final ContextElementUpdate context = ctx.context();
        final String key = ((ElementRich) context.element()).key();
        // TODO: portlek, Add more detailed message.
        context.manager().logger().debug("Element '%s' is updated.", key == null ? "null" : key);
    }

    private ServiceUpdateLogging() {}
}
