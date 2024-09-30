package net.infumia.inv.api.viewer;

import net.infumia.inv.api.context.view.ContextRender;
import org.jetbrains.annotations.NotNull;

public interface ContextualViewer extends Viewer {
    @NotNull
    ContextRender context();
}
