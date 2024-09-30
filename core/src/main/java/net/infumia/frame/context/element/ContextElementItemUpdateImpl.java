package net.infumia.frame.context.element;

import net.infumia.frame.element.ElementItemRich;
import org.jetbrains.annotations.NotNull;

public final class ContextElementItemUpdateImpl
    extends ContextElementUpdateImpl
    implements ContextElementItemUpdate {

    private final ElementItemRich element;

    public ContextElementItemUpdateImpl(
        @NotNull final ContextElementUpdate context,
        @NotNull final ElementItemRich element1
    ) {
        super(context);
        this.element = element1;
    }

    @NotNull
    @Override
    public ElementItemRich element() {
        return this.element;
    }
}
