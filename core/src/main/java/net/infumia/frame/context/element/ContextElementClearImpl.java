package net.infumia.frame.context.element;

import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.context.view.ContextRenderImpl;
import net.infumia.frame.element.Element;
import org.jetbrains.annotations.NotNull;

public final class ContextElementClearImpl
    extends ContextRenderImpl
    implements ContextElementClear {

    private final Element element;

    public ContextElementClearImpl(
        @NotNull final ContextRender context,
        @NotNull final Element element
    ) {
        super(context);
        this.element = element;
    }

    @NotNull
    @Override
    public Element element() {
        return this.element;
    }
}
