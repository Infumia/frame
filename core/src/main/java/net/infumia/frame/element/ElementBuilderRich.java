package net.infumia.frame.element;

import net.infumia.frame.context.ContextBase;
import org.jetbrains.annotations.NotNull;

public interface ElementBuilderRich extends ElementBuilder {
    @NotNull
    ElementBuilder root(@NotNull Element root);

    @NotNull
    Element build(@NotNull ContextBase parent);
}
