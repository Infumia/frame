package net.infumia.frame.context.element;

import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.element.Element;
import net.infumia.frame.service.Cancellable;
import org.jetbrains.annotations.NotNull;

public interface ContextElementUpdate extends ContextRender, Cancellable {
    @NotNull
    Element element();

    boolean forced();
}
