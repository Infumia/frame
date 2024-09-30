package net.infumia.inv.core.pipeline.context;

import net.infumia.inv.api.InventoryManager;
import net.infumia.inv.api.pipeline.context.PipelineContextState;
import net.infumia.inv.api.state.value.StateValue;
import net.infumia.inv.core.state.StateRich;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PipelineContextStates {
    final class Access implements PipelineContextState.Access {

        public final InventoryManager manager;
        private final StateRich<?> state;
        private final StateValue<?> value;

        public Access(
            @NotNull final InventoryManager manager,
            @NotNull final StateRich<?> state,
            @NotNull final StateValue<?> value
        ) {
            this.manager = manager;
            this.state = state;
            this.value = value;
        }

        @NotNull
        @Override
        public InventoryManager manager() {
            return this.manager;
        }

        @NotNull
        @Override
        public StateRich<?> state() {
            return this.state;
        }

        @NotNull
        @Override
        public StateValue<?> value() {
            return this.value;
        }
    }

    final class Update implements PipelineContextState.Update {

        public final InventoryManager manager;
        private final StateRich<?> state;
        private final Object oldValue;
        private final StateValue<?> value;

        public Update(
            @NotNull final InventoryManager manager,
            @NotNull final StateRich<?> state,
            @Nullable final Object oldValue,
            @NotNull final StateValue<?> value
        ) {
            this.manager = manager;
            this.state = state;
            this.oldValue = oldValue;
            this.value = value;
        }

        @NotNull
        @Override
        public InventoryManager manager() {
            return this.manager;
        }

        @NotNull
        @Override
        public StateRich<?> state() {
            return this.state;
        }

        @Nullable
        @Override
        public Object oldValue() {
            return this.oldValue;
        }

        @NotNull
        @Override
        public StateValue<?> value() {
            return this.value;
        }
    }
}
