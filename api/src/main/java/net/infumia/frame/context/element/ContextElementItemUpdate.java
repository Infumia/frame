package net.infumia.frame.context.element;

import net.infumia.frame.element.item.ElementItem;
import org.jetbrains.annotations.NotNull;

public interface ContextElementItemUpdate extends ContextElementUpdate {
    @NotNull
    @Override
    ElementItem element();
}
