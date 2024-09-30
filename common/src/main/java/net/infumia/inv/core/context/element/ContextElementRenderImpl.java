package net.infumia.inv.core.context.element;

import net.infumia.inv.api.context.element.ContextElementRender;
import net.infumia.inv.core.context.view.ContextRenderImpl;
import net.infumia.inv.core.context.view.ContextRenderRich;
import net.infumia.inv.core.element.ElementRich;
import org.jetbrains.annotations.NotNull;

public class ContextElementRenderImpl extends ContextRenderImpl implements ContextElementRender {

    private final ElementRich element;

    public ContextElementRenderImpl(
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

    public ContextElementRenderImpl(@NotNull final ContextElementRender context) {
        this((ContextRenderRich) context, (ElementRich) context.element());
    }
}
