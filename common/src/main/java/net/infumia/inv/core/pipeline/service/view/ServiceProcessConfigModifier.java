package net.infumia.inv.core.pipeline.service.view;

import net.infumia.inv.api.context.view.ContextOpen;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextView;
import net.infumia.inv.api.view.config.ViewConfigBuilder;
import net.infumia.inv.api.view.config.ViewConfigModifier;
import org.jetbrains.annotations.NotNull;

public final class ServiceProcessConfigModifier
    implements PipelineServiceConsumer<PipelineContextView.ProcessConfigModifier> {

    public static final PipelineServiceConsumer<
        PipelineContextView.ProcessConfigModifier
    > INSTANCE = new ServiceProcessConfigModifier();

    public static final String KEY = "process";

    @Override
    public String key() {
        return ServiceProcessConfigModifier.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextView.ProcessConfigModifier ctx) {
        final ContextOpen context = ctx.context();
        final ViewConfigBuilder configBuilder = context.modifyConfig();
        for (final ViewConfigModifier modifier : configBuilder.modifiers()) {
            modifier.accept(configBuilder, context);
        }
    }

    private ServiceProcessConfigModifier() {}
}
