package net.infumia.frame.pipeline.service.view;

import net.infumia.frame.context.ContextBaseRich;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextView;
import net.infumia.frame.state.StateRich;
import net.infumia.frame.state.value.StateValueHostRich;
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
