package net.infumia.frame.pipeline.service.render;

import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextRender;
import net.infumia.frame.state.value.StateValue;
import net.infumia.frame.state.value.StateValueHostRich;
import org.jetbrains.annotations.NotNull;

public final class ServiceFirstRenderInitializeState
    implements PipelineServiceConsumer<PipelineContextRender.FirstRender> {

    public static final PipelineServiceConsumer<PipelineContextRender.FirstRender> INSTANCE =
        new ServiceFirstRenderInitializeState();

    public static final String KEY = "initialize-state";

    @NotNull
    @Override
    public String key() {
        return ServiceFirstRenderInitializeState.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextRender.FirstRender ctx) {
        final ContextRender context = ctx.context();
        final StateValueHostRich host = (StateValueHostRich) context.stateValueHost();
        host
            .stateValues()
            .forEach((state, value) -> {
                final StateValue<Object> newValue = state.valueFactory().apply(context, state);
                if (newValue.mutable()) {
                    newValue.value(value.value());
                }
                host.initializeState(state, newValue);
            });
    }

    private ServiceFirstRenderInitializeState() {}
}
