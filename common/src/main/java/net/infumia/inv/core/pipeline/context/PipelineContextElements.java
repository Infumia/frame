package net.infumia.inv.core.pipeline.context;

import net.infumia.inv.api.context.element.ContextElementClear;
import net.infumia.inv.api.context.element.ContextElementClick;
import net.infumia.inv.api.context.element.ContextElementRender;
import net.infumia.inv.api.context.element.ContextElementUpdate;
import net.infumia.inv.api.pipeline.context.PipelineContextElement;
import org.jetbrains.annotations.NotNull;

public interface PipelineContextElements {
    final class Render implements PipelineContextElement.Render {

        private final ContextElementRender context;

        public Render(@NotNull final ContextElementRender context) {
            this.context = context;
        }

        @NotNull
        @Override
        public ContextElementRender context() {
            return this.context;
        }
    }

    final class Clear implements PipelineContextElement.Clear {

        private final ContextElementClear context;

        public Clear(@NotNull final ContextElementClear context) {
            this.context = context;
        }

        @NotNull
        @Override
        public ContextElementClear context() {
            return this.context;
        }
    }

    final class Update implements PipelineContextElement.Update {

        private final ContextElementUpdate context;

        private boolean cancelled;

        public Update(@NotNull final ContextElementUpdate context) {
            this.context = context;
        }

        @NotNull
        @Override
        public ContextElementUpdate context() {
            return this.context;
        }

        @Override
        public boolean cancelled() {
            return this.cancelled;
        }

        @Override
        public void cancelled(final boolean cancelled) {
            this.cancelled = cancelled;
        }
    }

    final class Click implements PipelineContextElement.Click {

        private final ContextElementClick context;

        private boolean cancelled;

        public Click(@NotNull final ContextElementClick context) {
            this.context = context;
        }

        @NotNull
        @Override
        public ContextElementClick context() {
            return this.context;
        }

        @Override
        public boolean cancelled() {
            return this.cancelled;
        }

        @Override
        public void cancelled(final boolean cancelled) {
            this.cancelled = cancelled;
        }
    }
}
