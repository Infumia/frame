package net.infumia.inv.core.viewer;

import net.infumia.inv.core.context.view.ContextRenderRich;
import org.jetbrains.annotations.NotNull;

public final class ContextualViewerImpl extends ViewerImpl implements ContextualViewerRich {

    private final ContextRenderRich context;

    public ContextualViewerImpl(
        @NotNull final ViewerRich viewer,
        @NotNull final ContextRenderRich context
    ) {
        super(viewer);
        this.context = context;
    }

    @NotNull
    @Override
    public ContextRenderRich context() {
        return this.context;
    }
}
