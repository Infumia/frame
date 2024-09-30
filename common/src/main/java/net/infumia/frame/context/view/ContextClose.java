package net.infumia.frame.context.view;

import net.infumia.frame.service.Cancellable;
import net.infumia.frame.viewer.ContextualViewer;
import org.jetbrains.annotations.NotNull;

public interface ContextClose extends ContextRender, Cancellable {
    @NotNull
    @Override
    ContextualViewer viewer();

    boolean forced();
}
