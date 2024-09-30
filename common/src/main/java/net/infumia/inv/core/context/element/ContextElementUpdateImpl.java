package net.infumia.inv.core.context.element;

import net.infumia.inv.api.context.element.ContextElementUpdate;
import net.infumia.inv.core.context.view.ContextRenderImpl;
import net.infumia.inv.core.context.view.ContextRenderRich;
import net.infumia.inv.core.element.ElementRich;
import org.jetbrains.annotations.NotNull;

public class ContextElementUpdateImpl extends ContextRenderImpl implements ContextElementUpdate {

    private final ElementRich element;
    private final boolean forced;
    private boolean cancelled;

    public ContextElementUpdateImpl(
        @NotNull final ContextRenderRich context,
        @NotNull final ElementRich element,
        final boolean forced
    ) {
        super(context);
        this.element = element;
        this.forced = forced;
    }

    public ContextElementUpdateImpl(@NotNull final ContextElementUpdate context) {
        this((ContextRenderRich) context, (ElementRich) context.element(), context.forced());
        this.cancelled = context.cancelled();
    }

    @NotNull
    @Override
    public ElementRich element() {
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
