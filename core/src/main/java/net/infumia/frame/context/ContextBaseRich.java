package net.infumia.frame.context;

import net.infumia.frame.viewer.Viewer;
import org.jetbrains.annotations.NotNull;

public interface ContextBaseRich extends ContextRich, ContextBase {
    void addViewer(@NotNull Viewer viewer);

    void removeViewer(@NotNull Viewer viewer);

    @NotNull
    Viewer viewerOrThrow(@NotNull String methodName);
}
