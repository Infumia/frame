package net.infumia.frame.context.element;

import net.infumia.frame.context.view.ContextRenderImpl;
import net.infumia.frame.context.view.ContextRenderRich;
import net.infumia.frame.element.ElementRich;
import org.jetbrains.annotations.NotNull;

public final class ContextElementClearImpl
    extends ContextRenderImpl
    implements ContextElementClear {

    private final ElementRich element;

    public ContextElementClearImpl(
        @NotNull final ContextRenderRich context,
        @NotNull final ElementRich element
    ) {
        super(context);
        this.element = element;
    }

    @NotNull
    @Override
    public ElementRich element() {
        return this.element;
    }
}
