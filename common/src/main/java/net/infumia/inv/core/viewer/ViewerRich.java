package net.infumia.inv.core.viewer;

import net.infumia.inv.api.viewer.Viewer;
import net.infumia.inv.core.view.ViewRich;
import org.jetbrains.annotations.NotNull;

public interface ViewerRich extends Viewer {
    @NotNull
    @Override
    ViewRich view();
}
