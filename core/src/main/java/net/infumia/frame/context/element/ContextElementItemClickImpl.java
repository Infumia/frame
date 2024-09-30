package net.infumia.frame.context.element;

import net.infumia.frame.element.ElementItem;
import org.jetbrains.annotations.NotNull;

public final class ContextElementItemClickImpl
    extends ContextElementClickImpl
    implements ContextElementItemClick {

    private final ElementItem element;

    public ContextElementItemClickImpl(
        @NotNull final ContextElementClick context,
        @NotNull final ElementItem element
    ) {
        super(context);
        this.element = element;
    }

    @NotNull
    @Override
    public ElementItem element() {
        return this.element;
    }
}
