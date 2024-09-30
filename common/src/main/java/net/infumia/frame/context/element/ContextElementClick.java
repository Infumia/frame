package net.infumia.frame.context.element;

import net.infumia.frame.context.view.ContextClick;
import net.infumia.frame.element.Element;
import org.jetbrains.annotations.NotNull;

public interface ContextElementClick extends ContextClick {
    @NotNull
    Element element();
}
