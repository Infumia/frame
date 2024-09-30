package net.infumia.frame.pipeline.context;

import net.infumia.frame.Frame;
import net.infumia.frame.state.State;
import net.infumia.frame.state.value.StateValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PipelineContextStates {
    final class Access implements PipelineContextState.Access {

        public final Frame frame;
        private final State<?> state;
        private final StateValue<?> value;

        public Access(
            @NotNull final Frame frame,
            @NotNull final State<?> state,
            @NotNull final StateValue<?> value
        ) {
            this.frame = frame;
            this.state = state;
            this.value = value;
        }

        @NotNull
        @Override
        public Frame frame() {
            return this.frame;
        }

        @NotNull
        @Override
        public State<?> state() {
            return this.state;
        }

        @NotNull
        @Override
        public StateValue<?> value() {
            return this.value;
        }
    }

    final class Update implements PipelineContextState.Update {

        public final Frame frame;
        private final State<?> state;
        private final Object oldValue;
        private final StateValue<?> value;

        public Update(
            @NotNull final Frame frame,
            @NotNull final State<?> state,
            @Nullable final Object oldValue,
            @NotNull final StateValue<?> value
        ) {
            this.frame = frame;
            this.state = state;
            this.oldValue = oldValue;
            this.value = value;
        }

        @NotNull
        @Override
        public Frame frame() {
            return this.frame;
        }

        @NotNull
        @Override
        public State<?> state() {
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
