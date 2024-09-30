package net.infumia.inv.api.context.element;

import net.infumia.inv.api.element.ElementItem;
import org.jetbrains.annotations.NotNull;

public interface ContextElementItemUpdate extends ContextElementUpdate {
    @NotNull
    @Override
    ElementItem element();
}
