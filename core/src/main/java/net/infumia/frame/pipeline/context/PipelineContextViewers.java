package net.infumia.frame.pipeline.context;

import java.util.Collection;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.viewer.Viewer;
import org.jetbrains.annotations.NotNull;

public interface PipelineContextViewers {
    final class Added implements PipelineContextViewer.Added {

        private final ContextRender context;
        private final Collection<Viewer> viewers;

        public Added(
            @NotNull final ContextRender context,
            @NotNull final Collection<Viewer> viewers
        ) {
            this.context = context;
            this.viewers = viewers;
        }

        @NotNull
        @Override
        public ContextRender context() {
            return this.context;
        }

        @NotNull
        @Override
        public Collection<Viewer> viewers() {
            return this.viewers;
        }
    }

    final class Removed implements PipelineContextViewer.Removed {

        private final ContextRender context;
        private final Collection<Viewer> viewers;

        public Removed(
            @NotNull final ContextRender context,
            @NotNull final Collection<Viewer> viewers
        ) {
            this.context = context;
            this.viewers = viewers;
        }

        @NotNull
        @Override
        public ContextRender context() {
            return this.context;
        }

        @NotNull
        @Override
        public Collection<Viewer> viewers() {
            return this.viewers;
        }
    }
}
