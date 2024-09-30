package net.infumia.inv.core.pipeline.context;

import java.util.Collection;
import net.infumia.inv.api.pipeline.context.PipelineContextViewer;
import net.infumia.inv.api.viewer.Viewer;
import net.infumia.inv.core.context.view.ContextRenderRich;
import org.jetbrains.annotations.NotNull;

public interface PipelineContextViewers {
    final class Added implements PipelineContextViewer.Added {

        private final ContextRenderRich context;
        private final Collection<Viewer> viewers;

        public Added(
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

    final class Removed implements PipelineContextViewer.Removed {

        private final ContextRenderRich context;
        private final Collection<Viewer> viewers;

        public Removed(
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
}
