package net.infumia.inv.core.pipeline.service.view;

import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextView;
import org.jetbrains.annotations.NotNull;

public final class ServiceProcessConfigModifierLogging
    implements PipelineServiceConsumer<PipelineContextView.ProcessConfigModifier> {

    public static final PipelineServiceConsumer<
        PipelineContextView.ProcessConfigModifier
    > INSTANCE = new ServiceProcessConfigModifierLogging();

    public static final String KEY = "logging";

    @Override
    public String key() {
        return ServiceProcessConfigModifierLogging.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextView.ProcessConfigModifier ctx) {
        ctx.context().manager().logger().debug("Config modifiers are proceed.");
    }

    private ServiceProcessConfigModifierLogging() {}
}
