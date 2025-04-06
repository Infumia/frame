package net.infumia.frame.context.view;

import java.util.Collection;
import net.infumia.frame.viewer.Viewer;
import org.jetbrains.annotations.NotNull;

public interface ContextResume extends ContextRender {
    @NotNull
    ContextRender from();

    @NotNull
    Collection<Viewer> resumedViewers();
}
