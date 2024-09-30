package net.infumia.inv.core.context.element;

import net.infumia.inv.api.context.element.ContextElementClear;
import net.infumia.inv.core.context.view.ContextRenderImpl;
import net.infumia.inv.core.context.view.ContextRenderRich;
import net.infumia.inv.core.element.ElementRich;
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
