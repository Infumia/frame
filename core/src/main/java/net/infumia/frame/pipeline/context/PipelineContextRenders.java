package net.infumia.frame.pipeline.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.infumia.frame.context.view.ContextRenderRich;
import net.infumia.frame.context.view.ContextResume;
import net.infumia.frame.element.Element;
import net.infumia.frame.element.ElementRich;
import net.infumia.frame.viewer.Viewer;
import org.jetbrains.annotations.NotNull;

public interface PipelineContextRenders {
    final class FirstRender implements PipelineContextRender.FirstRender {

        private final List<ElementRich> elements = new ArrayList<>();
        private final ContextRenderRich context;

        public FirstRender(@NotNull final ContextRenderRich context) {
            this.context = context;
        }

        @NotNull
        @Override
        public ContextRenderRich context() {
            return this.context;
        }

        @NotNull
        @Override
        public List<Element> elements() {
            return Collections.unmodifiableList(this.elements);
        }

        @Override
        public void addElement(@NotNull final Element element) {
            this.elements.add(0, (ElementRich) element);
        }
    }

    final class OpenContainer implements PipelineContextRender.OpenContainer {

        private final ContextRenderRich context;
        private final Collection<Viewer> viewers;

        public OpenContainer(
            @NotNull final ContextRenderRich context,
            @NotNull final Collection<Viewer> viewers
        ) {
            this.context = context;
            this.viewers = viewers;
        }

        @NotNull
        @Override
        public ContextRenderRich context() {
            return this.context;
        }

        @NotNull
        @Override
        public Collection<Viewer> viewers() {
            return this.viewers;
        }
    }

    final class Resume implements PipelineContextRender.Resume {

        private final ContextResume context;

        public Resume(@NotNull final ContextResume context) {
            this.context = context;
        }

        @NotNull
        @Override
        public ContextResume context() {
            return this.context;
        }
    }

    final class CloseContainer implements PipelineContextRender.CloseContainer {

        private final ContextRenderRich context;
        private final Collection<Viewer> viewers;

        public CloseContainer(
            @NotNull final ContextRenderRich context,
            @NotNull final Collection<Viewer> viewers
        ) {
            this.context = context;
            this.viewers = viewers;
        }

        @NotNull
        @Override
        public ContextRenderRich context() {
            return this.context;
        }

        @NotNull
        @Override
        public Collection<Viewer> viewers() {
            return this.viewers;
        }
    }

    final class StartUpdate implements PipelineContextRender.StartUpdate {

        private final ContextRenderRich context;
        private boolean cancelled;

        public StartUpdate(@NotNull final ContextRenderRich context) {
            this.context = context;
        }

        @NotNull
        @Override
        public ContextRenderRich context() {
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

    final class StopUpdate implements PipelineContextRender.StopUpdate {

        private final ContextRenderRich context;

        public StopUpdate(@NotNull final ContextRenderRich context) {
            this.context = context;
        }

        @NotNull
        @Override
        public ContextRenderRich context() {
            return this.context;
        }
    }

    final class Update implements PipelineContextRender.Update {

        private final ContextRenderRich context;

        public Update(@NotNull final ContextRenderRich context) {
            this.context = context;
        }

        @NotNull
        @Override
        public ContextRenderRich context() {
            return this.context;
        }
    }
}
