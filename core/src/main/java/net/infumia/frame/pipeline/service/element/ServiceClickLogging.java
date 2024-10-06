package net.infumia.frame.pipeline.service.element;

import net.infumia.frame.context.element.ContextElementClick;
import net.infumia.frame.element.ElementRich;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextElement;
import org.jetbrains.annotations.NotNull;

public final class ServiceClickLogging
    implements PipelineServiceConsumer<PipelineContextElement.Click> {

    public static final PipelineServiceConsumer<PipelineContextElement.Click> INSTANCE =
        new ServiceClickLogging();

    public static final String KEY = "logging";

    @NotNull
    @Override
    public String key() {
        return ServiceClickLogging.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextElement.Click ctx) {
        final ContextElementClick context = ctx.context();
        final String key = ((ElementRich) context.element()).key();
        // TODO: portlek, Add more detailed message.
        context.frame().logger().debug("Element '%s' is clicked.", key == null ? "null" : key);
    }

    private ServiceClickLogging() {}
}
