package net.infumia.inv.api.pipeline.context;

import java.util.Collection;
import java.util.List;
import net.infumia.inv.api.context.view.ContextRender;
import net.infumia.inv.api.context.view.ContextResume;
import net.infumia.inv.api.element.Element;
import net.infumia.inv.api.pipeline.PipelineContext;
import net.infumia.inv.api.service.Cancellable;
import net.infumia.inv.api.viewer.Viewer;
import org.jetbrains.annotations.NotNull;

public interface PipelineContextRender extends PipelineContext {
    @NotNull
    ContextRender context();

    interface FirstRender extends PipelineContextRender {
        @NotNull
        List<Element> elements();

        void addElement(@NotNull Element element);
    }

    interface OpenContainer extends PipelineContextRender {
        @NotNull
        Collection<Viewer> viewers();
    }

    interface Resume extends PipelineContextRender {
        @NotNull
        @Override
        ContextResume context();
    }

    interface CloseContainer extends PipelineContextRender {
        @NotNull
        Collection<Viewer> viewers();
    }

    interface StartUpdate extends PipelineContextRender, Cancellable {}

    interface StopUpdate extends PipelineContextRender {}

    interface Update extends PipelineContextRender {}
}
