package net.infumia.inv.core.context.element;

import net.infumia.inv.api.context.element.ContextElementClick;
import net.infumia.inv.api.context.view.ContextClick;
import net.infumia.inv.core.context.view.ContextClickImpl;
import net.infumia.inv.core.element.ElementRich;
import org.jetbrains.annotations.NotNull;

public class ContextElementClickImpl extends ContextClickImpl implements ContextElementClick {

    private final ElementRich element;

    public ContextElementClickImpl(
        @NotNull final ContextClick context,
        @NotNull final ElementRich element
    ) {
        super(context);
        this.element = element;
    }

    public ContextElementClickImpl(@NotNull final ContextElementClick context) {
        this(context, (ElementRich) context.element());
    }

    @NotNull
    @Override
    public ElementRich element() {
        return this.element;
    }
}
