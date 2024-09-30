package net.infumia.frame.context.element;

import net.infumia.frame.context.view.ContextClick;
import net.infumia.frame.context.view.ContextClickImpl;
import net.infumia.frame.element.Element;
import org.jetbrains.annotations.NotNull;

public class ContextElementClickImpl extends ContextClickImpl implements ContextElementClick {

    private final Element element;

    public ContextElementClickImpl(
        @NotNull final ContextClick context,
        @NotNull final Element element
    ) {
        super(context);
        this.element = element;
    }

    public ContextElementClickImpl(@NotNull final ContextElementClick context) {
        this(context, context.element());
    }

    @NotNull
    @Override
    public Element element() {
        return this.element;
    }
}
