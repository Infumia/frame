package net.infumia.frame.context.view;

import net.infumia.frame.viewer.ContextualViewer;
import org.jetbrains.annotations.NotNull;

public final class ContextCloseImpl extends ContextRenderImpl implements ContextClose {

    private final ContextualViewer viewer;
    private final boolean forced;
    private boolean cancelled;

    public ContextCloseImpl(@NotNull final ContextualViewer viewer, final boolean forced) {
        super(viewer.context());
        this.viewer = viewer;
        this.forced = forced;
    }

    @NotNull
    @Override
    public ContextualViewer viewer() {
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
