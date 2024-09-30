package net.infumia.frame.pipeline.context;

import net.infumia.frame.Frame;
import net.infumia.frame.pipeline.PipelineContext;
import net.infumia.frame.state.State;
import net.infumia.frame.state.value.StateValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PipelineContextState extends PipelineContext {
    @NotNull
    Frame manager();

    @NotNull
    State<?> state();

    interface Access extends PipelineContextState {
        @NotNull
        StateValue<?> value();
    }

    interface Update extends PipelineContextState {
        @Nullable
        Object oldValue();

        @NotNull
        StateValue<?> value();
    }
}
