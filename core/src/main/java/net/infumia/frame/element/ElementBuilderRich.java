package net.infumia.frame.element;

import net.infumia.frame.context.ContextBase;
import org.jetbrains.annotations.NotNull;

public interface ElementBuilderRich extends ElementBuilder {
    @NotNull
    ElementBuilderRich root(@NotNull ElementRich root);

    @NotNull
    ElementRich build(@NotNull ContextBase parent);
}
