package net.infumia.inv.api.pipeline.context;

import net.infumia.inv.api.InventoryManager;
import net.infumia.inv.api.pipeline.PipelineContext;
import net.infumia.inv.api.state.State;
import net.infumia.inv.api.state.value.StateValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PipelineContextState extends PipelineContext {
    @NotNull
    InventoryManager manager();

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
