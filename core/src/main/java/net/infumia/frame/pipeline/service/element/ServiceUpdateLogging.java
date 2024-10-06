package net.infumia.frame.pipeline.service.element;

import net.infumia.frame.context.element.ContextElementUpdate;
import net.infumia.frame.element.ElementRich;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextElement;
import org.jetbrains.annotations.NotNull;

public final class ServiceUpdateLogging
    implements PipelineServiceConsumer<PipelineContextElement.Update> {

    public static final PipelineServiceConsumer<PipelineContextElement.Update> INSTANCE =
        new ServiceUpdateLogging();

    public static final String KEY = "logging";

    @NotNull
    @Override
    public String key() {
        return ServiceUpdateLogging.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextElement.Update ctx) {
        final ContextElementUpdate context = ctx.context();
        final String key = ((ElementRich) context.element()).key();
        // TODO: portlek, Add more detailed message.
        context.frame().logger().debug("Element '%s' is updated.", key == null ? "null" : key);
    }

    private ServiceUpdateLogging() {}
}
