package net.infumia.frame.context.element;

import net.infumia.frame.element.ElementItem;
import org.jetbrains.annotations.NotNull;

public final class ContextElementItemUpdateImpl
    extends ContextElementUpdateImpl
    implements ContextElementItemUpdate {

    private final ElementItem element;

    public ContextElementItemUpdateImpl(
        @NotNull final ContextElementUpdate context,
        @NotNull final ElementItem element1
    ) {
        super(context);
        this.element = element1;
    }

    @NotNull
    @Override
    public ElementItem element() {
        return this.element;
    }
}
