package net.infumia.frame.context.element;

import net.infumia.frame.element.ElementItem;
import org.jetbrains.annotations.NotNull;

public interface ContextElementItemClick extends ContextElementClick {
    @NotNull
    @Override
    ElementItem element();
}
