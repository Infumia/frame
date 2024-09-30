package net.infumia.frame.element;

import org.jetbrains.annotations.NotNull;

public interface ElementItemRich extends ElementRich, ElementItem, ElementEventHandlerHolder {
    @NotNull
    @Override
    ElementItemBuilderRich toBuilder();
}
