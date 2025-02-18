package net.infumia.frame.pipeline.context;

import java.util.Collection;
import java.util.List;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.context.view.ContextResume;
import net.infumia.frame.element.Element;
import net.infumia.frame.service.Cancellable;
import net.infumia.frame.viewer.Viewer;
import org.jetbrains.annotations.NotNull;

public interface PipelineContextRender {
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
