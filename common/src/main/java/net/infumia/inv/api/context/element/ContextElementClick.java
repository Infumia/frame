package net.infumia.inv.api.context.element;

import net.infumia.inv.api.context.view.ContextClick;
import net.infumia.inv.api.element.Element;
import org.jetbrains.annotations.NotNull;

public interface ContextElementClick extends ContextClick {
    @NotNull
    Element element();
}
