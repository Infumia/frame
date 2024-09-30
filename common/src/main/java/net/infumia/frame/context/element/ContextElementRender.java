package net.infumia.frame.context.element;

import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.element.Element;
import org.jetbrains.annotations.NotNull;

public interface ContextElementRender extends ContextRender {
    @NotNull
    Element element();
}
