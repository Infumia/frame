package net.infumia.frame.context.element;

import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.context.view.ContextRenderImpl;
import net.infumia.frame.element.Element;
import org.jetbrains.annotations.NotNull;

public class ContextElementRenderImpl extends ContextRenderImpl implements ContextElementRender {

    private final Element element;

    public ContextElementRenderImpl(
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

    public ContextElementRenderImpl(@NotNull final ContextElementRender context) {
        this(context, context.element());
    }
}
