package net.infumia.inv.core.viewer;

import net.infumia.inv.api.viewer.ContextualViewer;
import net.infumia.inv.core.context.view.ContextRenderRich;
import org.jetbrains.annotations.NotNull;

public interface ContextualViewerRich extends ViewerRich, ContextualViewer {
    @NotNull
    @Override
    ContextRenderRich context();
}
