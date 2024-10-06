package net.infumia.frame.pipeline.service.element;

import net.infumia.frame.context.element.ContextElementClear;
import net.infumia.frame.element.ElementRich;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextElement;
import org.jetbrains.annotations.NotNull;

public final class ServiceClearLogging
    implements PipelineServiceConsumer<PipelineContextElement.Clear> {

    public static final PipelineServiceConsumer<PipelineContextElement.Clear> INSTANCE =
        new ServiceClearLogging();

    public static final String KEY = "logging";

    @NotNull
    @Override
    public String key() {
        return ServiceClearLogging.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextElement.Clear ctx) {
        final ContextElementClear context = ctx.context();
        final String key = ((ElementRich) context.element()).key();
        // TODO: portlek, Add more detailed message.
        context.frame().logger().debug("Element '%s' is cleared.", key == null ? "null" : key);
    }

    private ServiceClearLogging() {}
}
