package net.infumia.inv.api.context.element;

import net.infumia.inv.api.element.ElementItem;
import org.jetbrains.annotations.NotNull;

public interface ContextElementItemClick extends ContextElementClick {
    @NotNull
    @Override
    ElementItem element();
}
