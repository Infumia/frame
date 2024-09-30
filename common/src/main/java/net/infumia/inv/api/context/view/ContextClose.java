package net.infumia.inv.api.context.view;

import net.infumia.inv.api.service.Cancellable;
import net.infumia.inv.api.viewer.ContextualViewer;
import org.jetbrains.annotations.NotNull;

public interface ContextClose extends ContextRender, Cancellable {
    @Override
    @NotNull
    ContextualViewer viewer();

    boolean forced();
}
