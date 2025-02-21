package net.infumia.frame.element;

import net.infumia.frame.context.ContextBase;
import org.jetbrains.annotations.NotNull;

public interface ElementBuilderRich<This extends ElementBuilderRich<This>>
    extends ElementBuilder<This> {
    void root(@NotNull Element root);

    @NotNull
    Element build(@NotNull ContextBase parent);
}
