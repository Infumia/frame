package net.infumia.inv.api.pipeline.context;

import java.util.Collection;
import net.infumia.inv.api.context.view.ContextRender;
import net.infumia.inv.api.pipeline.PipelineContext;
import net.infumia.inv.api.viewer.Viewer;
import org.jetbrains.annotations.NotNull;

public interface PipelineContextViewer extends PipelineContext {
    @NotNull
    ContextRender context();

    interface Added extends PipelineContextViewer {
        @NotNull
        Collection<Viewer> viewers();
    }

    interface Removed extends PipelineContextViewer {
        @NotNull
        Collection<Viewer> viewers();
    }
}
