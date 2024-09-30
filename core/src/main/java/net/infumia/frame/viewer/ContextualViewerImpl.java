package net.infumia.frame.viewer;

import net.infumia.frame.context.view.ContextRender;
import org.jetbrains.annotations.NotNull;

public final class ContextualViewerImpl extends ViewerImpl implements ContextualViewerRich {

    private final ContextRender context;

    public ContextualViewerImpl(
        @NotNull final Viewer viewer,
        @NotNull final ContextRender context
    ) {
        super(viewer);
        this.context = context;
    }

    @NotNull
    @Override
    public ContextRender context() {
        return this.context;
    }
}
