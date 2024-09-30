package net.infumia.inv.core.context;

import net.infumia.inv.api.context.ContextBase;
import net.infumia.inv.core.config.ViewConfigRich;
import net.infumia.inv.core.view.ViewRich;
import net.infumia.inv.core.viewer.ViewerRich;
import org.jetbrains.annotations.NotNull;

public interface ContextBaseRich extends ContextRich, ContextBase {
    @NotNull
    @Override
    ViewRich view();

    @NotNull
    @Override
    ViewConfigRich initialConfig();

    @NotNull
    @Override
    ViewerRich viewer();

    void addViewer(@NotNull ViewerRich viewer);

    void removeViewer(@NotNull ViewerRich viewer);

    @NotNull
    ViewerRich viewerOrThrow(@NotNull String methodName);
}
