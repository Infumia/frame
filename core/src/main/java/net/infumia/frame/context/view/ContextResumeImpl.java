package net.infumia.frame.context.view;

import java.util.Collection;
import net.infumia.frame.viewer.Viewer;
import org.jetbrains.annotations.NotNull;

public final class ContextResumeImpl extends ContextRenderImpl implements ContextResume {

    private final ContextRender from;
    private final Collection<Viewer> resumedViewers;

    public ContextResumeImpl(
        @NotNull final ContextRender context,
        @NotNull final ContextRender from,
        @NotNull final Collection<Viewer> resumedViewers
    ) {
        super(context);
        this.from = from;
        this.resumedViewers = resumedViewers;
    }

    @NotNull
    @Override
    public ContextRender from() {
        return this.from;
    }

    @NotNull
    @Override
    public Collection<Viewer> resumedViewers() {
        return this.resumedViewers;
    }
}
