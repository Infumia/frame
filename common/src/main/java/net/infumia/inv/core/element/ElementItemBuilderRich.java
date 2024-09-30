package net.infumia.inv.core.element;

import net.infumia.inv.api.context.ContextBase;
import net.infumia.inv.api.element.ElementItemBuilder;
import org.jetbrains.annotations.NotNull;

public interface ElementItemBuilderRich extends ElementBuilderRich, ElementItemBuilder {
    @NotNull
    @Override
    ElementItemRich build(@NotNull ContextBase parent);

    int slot();
}
