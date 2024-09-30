package net.infumia.frame.pipeline.context;

import net.infumia.frame.context.element.ContextElementClear;
import net.infumia.frame.context.element.ContextElementClick;
import net.infumia.frame.context.element.ContextElementRender;
import net.infumia.frame.context.element.ContextElementUpdate;
import net.infumia.frame.pipeline.PipelineContext;
import net.infumia.frame.service.Cancellable;
import org.jetbrains.annotations.NotNull;

public interface PipelineContextElement extends PipelineContext {
    interface Render extends PipelineContextElement {
        @NotNull
        ContextElementRender context();
    }

    interface Clear extends PipelineContextElement {
        @NotNull
        ContextElementClear context();
    }

    interface Update extends PipelineContextElement, Cancellable {
        @NotNull
        ContextElementUpdate context();
    }

    interface Click extends PipelineContextElement, Cancellable {
        @NotNull
        ContextElementClick context();
    }
}
