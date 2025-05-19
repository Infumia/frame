package net.infumia.frame.pipeline.context;

import java.util.Collection;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.viewer.Viewer;
import org.jetbrains.annotations.NotNull;

public interface PipelineContextViewer {
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
