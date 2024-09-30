package net.infumia.inv.api.context.element;

import net.infumia.inv.api.context.view.ContextRender;
import net.infumia.inv.api.element.Element;
import org.jetbrains.annotations.NotNull;

public interface ContextElementRender extends ContextRender {
    @NotNull
    Element element();
}
