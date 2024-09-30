package net.infumia.inv.core.element;

import net.infumia.inv.api.context.ContextBase;
import net.infumia.inv.api.element.ElementBuilder;
import org.jetbrains.annotations.NotNull;

public interface ElementBuilderRich extends ElementBuilder {
    @NotNull
    ElementBuilderRich root(@NotNull ElementRich root);

    @NotNull
    ElementRich build(@NotNull ContextBase parent);
}
