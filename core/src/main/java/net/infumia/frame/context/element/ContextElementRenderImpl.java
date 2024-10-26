package net.infumia.frame.context.element;

import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.context.view.ContextRenderImpl;
import net.infumia.frame.element.Element;
import org.jetbrains.annotations.NotNull;

public class ContextElementRenderImpl extends ContextRenderImpl implements ContextElementRender {

    private final Element element;
    private final boolean forced;

    public ContextElementRenderImpl(
        @NotNull final ContextRender context,
        @NotNull final Element element,
        final boolean forced
    ) {
        super(context);
        this.element = element;
        this.forced = forced;
    }

    public ContextElementRenderImpl(@NotNull final ContextElementRender context) {
        this(context, context.element(), context.forced());
    }

    @NotNull
    @Override
    public Element element() {
        return this.element;
    }

    @Override
    public boolean forced() {
        return this.forced;
    }
}
