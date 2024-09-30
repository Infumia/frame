package net.infumia.frame.viewer;

import net.infumia.frame.context.view.ContextRender;
import org.jetbrains.annotations.NotNull;

public interface ContextualViewer extends Viewer {
    @NotNull
    ContextRender context();
}
