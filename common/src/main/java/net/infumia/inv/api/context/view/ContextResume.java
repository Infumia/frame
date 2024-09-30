package net.infumia.inv.api.context.view;

import java.util.Collection;
import net.infumia.inv.api.viewer.Viewer;
import org.jetbrains.annotations.NotNull;

public interface ContextResume extends ContextRender {
    @NotNull
    ContextRender from();

    @NotNull
    Collection<Viewer> resumedViewers();
}
