package net.infumia.frame.context.view;

import net.infumia.frame.service.Cancellable;

public interface ContextClose extends ContextRender, Cancellable {
    boolean forced();
}
