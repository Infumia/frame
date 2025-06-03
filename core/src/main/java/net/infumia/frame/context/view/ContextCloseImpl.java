package net.infumia.frame.context.view;

import net.infumia.frame.viewer.Viewer;
import org.jetbrains.annotations.NotNull;

public final class ContextCloseImpl extends ContextRenderImpl implements ContextClose {

    private final Viewer viewer;
    private final boolean forced;
    private boolean cancelled;

    public ContextCloseImpl(
        @NotNull final ContextRender ctx,
        @NotNull final Viewer viewer,
        final boolean forced
    ) {
        super(ctx);
        this.viewer = viewer;
        this.forced = forced;
    }

    @NotNull
    @Override
    public Viewer viewer() {
        return this.viewer;
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
