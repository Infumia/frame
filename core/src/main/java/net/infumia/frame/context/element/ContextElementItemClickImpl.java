package net.infumia.frame.context.element;

import net.infumia.frame.element.ElementItemRich;
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
