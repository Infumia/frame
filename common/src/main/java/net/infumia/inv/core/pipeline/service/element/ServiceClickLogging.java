package net.infumia.inv.core.pipeline.service.element;

import net.infumia.inv.api.context.element.ContextElementClick;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextElement;
import net.infumia.inv.core.element.ElementRich;
import org.jetbrains.annotations.NotNull;

public final class ServiceClickLogging
    implements PipelineServiceConsumer<PipelineContextElement.Click> {

    public static final PipelineServiceConsumer<PipelineContextElement.Click> INSTANCE =
        new ServiceClickLogging();

    public static final String KEY = "logging";

    @Override
    public String key() {
        return ServiceClickLogging.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextElement.Click ctx) {
        final ContextElementClick context = ctx.context();
        final String key = ((ElementRich) context.element()).key();
        // TODO: portlek, Add more detailed message.
        context.manager().logger().debug("Element '%s' is clicked.", key == null ? "null" : key);
    }

    private ServiceClickLogging() {}
}
