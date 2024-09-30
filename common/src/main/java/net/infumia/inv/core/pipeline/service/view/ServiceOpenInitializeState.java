package net.infumia.inv.core.pipeline.service.view;

import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextView;
import net.infumia.inv.core.context.ContextBaseRich;
import net.infumia.inv.core.state.StateRich;
import net.infumia.inv.core.state.value.StateValueHostRich;
import org.jetbrains.annotations.NotNull;

public final class ServiceOpenInitializeState
    implements PipelineServiceConsumer<PipelineContextView.Open> {

    public static final PipelineServiceConsumer<PipelineContextView.Open> INSTANCE =
        new ServiceOpenInitializeState();

    public static final String KEY = "initialize-state";

    @Override
    public String key() {
        return ServiceOpenInitializeState.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextView.Open ctx) {
        final ContextBaseRich context = (ContextBaseRich) ctx.context();
        final StateValueHostRich host = (StateValueHostRich) context.stateValueHost();
        for (final StateRich<Object> state : context.stateRegistry()) {
            host.initializeState(state, state.valueFactory().apply(context, state));
        }
    }

    private ServiceOpenInitializeState() {}
}
