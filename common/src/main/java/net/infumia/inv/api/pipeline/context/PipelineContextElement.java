package net.infumia.inv.api.pipeline.context;

import net.infumia.inv.api.context.element.ContextElementClear;
import net.infumia.inv.api.context.element.ContextElementClick;
import net.infumia.inv.api.context.element.ContextElementRender;
import net.infumia.inv.api.context.element.ContextElementUpdate;
import net.infumia.inv.api.pipeline.PipelineContext;
import net.infumia.inv.api.service.Cancellable;
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
