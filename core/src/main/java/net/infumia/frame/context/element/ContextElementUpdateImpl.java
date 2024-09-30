package net.infumia.frame.context.element;

import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.context.view.ContextRenderImpl;
import net.infumia.frame.element.Element;
import org.jetbrains.annotations.NotNull;

public class ContextElementUpdateImpl extends ContextRenderImpl implements ContextElementUpdate {

    private final Element element;
    private final boolean forced;
    private boolean cancelled;

    public ContextElementUpdateImpl(
        @NotNull final ContextRender context,
        @NotNull final Element element,
        final boolean forced
    ) {
        super(context);
        this.element = element;
        this.forced = forced;
    }

    public ContextElementUpdateImpl(@NotNull final ContextElementUpdate context) {
        this(context, context.element(), context.forced());
        this.cancelled = context.cancelled();
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

    @Override
    public boolean cancelled() {
        return this.cancelled;
    }

    @Override
    public void cancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
}
