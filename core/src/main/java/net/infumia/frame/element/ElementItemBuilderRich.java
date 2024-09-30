package net.infumia.frame.element;

import net.infumia.frame.context.ContextBase;
import org.jetbrains.annotations.NotNull;

public interface ElementItemBuilderRich extends ElementBuilderRich, ElementItemBuilder {
    @NotNull
    @Override
    ElementItem build(@NotNull ContextBase parent);

    int slot();
}
