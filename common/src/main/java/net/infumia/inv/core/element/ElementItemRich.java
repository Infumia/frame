package net.infumia.inv.core.element;

import net.infumia.inv.api.element.ElementItem;
import org.jetbrains.annotations.NotNull;

public interface ElementItemRich extends ElementRich, ElementItem, ElementEventHandlerHolder {
    @NotNull
    @Override
    ElementItemBuilderRich toBuilder();
}
