package net.infumia.frame.pipeline.service.view;

import net.infumia.frame.context.view.ContextOpen;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextView;
import net.infumia.frame.view.config.ViewConfigBuilder;
import net.infumia.frame.view.config.ViewConfigModifier;
import org.jetbrains.annotations.NotNull;

public final class ServiceProcessConfigModifier
    implements PipelineServiceConsumer<PipelineContextView.ProcessConfigModifier> {

    public static final PipelineServiceConsumer<
        PipelineContextView.ProcessConfigModifier
    > INSTANCE = new ServiceProcessConfigModifier();

    public static final String KEY = "process";

    @NotNull
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
