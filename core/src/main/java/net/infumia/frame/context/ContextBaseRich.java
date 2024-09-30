package net.infumia.frame.context;

import net.infumia.frame.viewer.Viewer;
import org.jetbrains.annotations.NotNull;

public interface ContextBaseRich extends ContextRich, ContextBase {
    void addViewer(@NotNull ViewerRich viewer);

    void removeViewer(@NotNull ViewerRich viewer);

    @NotNull
    Viewer viewerOrThrow(@NotNull String methodName);
}
