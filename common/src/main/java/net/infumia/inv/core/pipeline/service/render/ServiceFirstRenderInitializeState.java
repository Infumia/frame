package net.infumia.inv.core.pipeline.service.render;

import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextRender;
import net.infumia.inv.api.state.value.StateValue;
import net.infumia.inv.core.context.view.ContextRenderRich;
import net.infumia.inv.core.state.value.StateValueHostRich;
import org.jetbrains.annotations.NotNull;

public final class ServiceFirstRenderInitializeState
    implements PipelineServiceConsumer<PipelineContextRender.FirstRender> {

    public static final PipelineServiceConsumer<PipelineContextRender.FirstRender> INSTANCE =
        new ServiceFirstRenderInitializeState();

    public static final String KEY = "initialize-state";

    @Override
    public String key() {
        return ServiceFirstRenderInitializeState.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextRender.FirstRender ctx) {
        final ContextRenderRich context = (ContextRenderRich) ctx.context();
        final StateValueHostRich host = (StateValueHostRich) context.stateValueHost();
        host
            .stateValues()
            .forEach((state, value) -> {
                final StateValue<Object> newValue = state.valueFactory().apply(context, state);
                if (newValue.mutable()) {
                    newValue.value(value);
                }
                host.initializeState(state, newValue);
            });
    }

    private ServiceFirstRenderInitializeState() {}
}
