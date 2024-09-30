package net.infumia.inv.core.pipeline.service.element;

import net.infumia.inv.api.context.element.ContextElementClear;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextElement;
import net.infumia.inv.core.element.ElementRich;
import org.jetbrains.annotations.NotNull;

public final class ServiceClearLogging
    implements PipelineServiceConsumer<PipelineContextElement.Clear> {

    public static final PipelineServiceConsumer<PipelineContextElement.Clear> INSTANCE =
        new ServiceClearLogging();

    public static final String KEY = "logging";

    @Override
    public String key() {
        return ServiceClearLogging.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextElement.Clear ctx) {
        final ContextElementClear context = ctx.context();
        final String key = ((ElementRich) context.element()).key();
        // TODO: portlek, Add more detailed message.
        context.manager().logger().debug("Element '%s' is cleared.", key == null ? "null" : key);
    }

    private ServiceClearLogging() {}
}
