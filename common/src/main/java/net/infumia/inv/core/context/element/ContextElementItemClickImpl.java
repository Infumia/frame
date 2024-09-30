package net.infumia.inv.core.context.element;

import net.infumia.inv.api.context.element.ContextElementClick;
import net.infumia.inv.api.context.element.ContextElementItemClick;
import net.infumia.inv.core.element.ElementItemRich;
import org.jetbrains.annotations.NotNull;

public final class ContextElementItemClickImpl
    extends ContextElementClickImpl
    implements ContextElementItemClick {

    private final ElementItemRich element;

    public ContextElementItemClickImpl(
        @NotNull final ContextElementClick context,
        @NotNull final ElementItemRich element
    ) {
        super(context);
        this.element = element;
    }

    @NotNull
    @Override
    public ElementItemRich element() {
        return this.element;
    }
}
