package net.infumia.frame.pipeline.executor;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.context.element.ContextElementClearImpl;
import net.infumia.frame.context.element.ContextElementClickImpl;
import net.infumia.frame.context.element.ContextElementRenderImpl;
import net.infumia.frame.context.element.ContextElementUpdateImpl;
import net.infumia.frame.context.view.ContextClick;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.element.ElementRich;
import net.infumia.frame.pipeline.context.PipelineContextElement;
import net.infumia.frame.pipeline.context.PipelineContextElements;
import net.infumia.frame.pipeline.holder.PipelineHolderElement;
import net.infumia.frame.service.ConsumerService;
import net.infumia.frame.service.Implementation;
import org.jetbrains.annotations.NotNull;

public final class PipelinesElementImpl implements PipelinesElement {

    private final PipelineHolderElement pipelines = PipelineHolderElement.BASE.cloned();
    private final ElementRich element;

    public PipelinesElementImpl(@NotNull final ElementRich element) {
        this.element = element;
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> executeRender(
        @NotNull final ContextRender context,
        final boolean forced
    ) {
        return this.pipelines.render().completeWith(
            new PipelineContextElements.Render(
                new ContextElementRenderImpl(context, this.element, forced)
            )
        );
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> executeUpdate(
        @NotNull final ContextRender context,
        final boolean forced
    ) {
        return this.pipelines.update().completeWith(
            new PipelineContextElements.Update(
                new ContextElementUpdateImpl(context, this.element, forced)
            )
        );
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> executeClick(
        @NotNull final ContextClick context
    ) {
        return this.pipelines.click().completeWith(
            new PipelineContextElements.Click(new ContextElementClickImpl(context, this.element))
        );
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> executeClear(
        @NotNull final ContextRender context
    ) {
        return this.pipelines.clear().completeWith(
            new PipelineContextElements.Clear(new ContextElementClearImpl(context, this.element))
        );
    }

    @Override
    public void applyRender(
        @NotNull final Implementation<
            PipelineContextElement.Render,
            ConsumerService.State
        > implementation
    ) {
        this.pipelines.render().apply(implementation);
    }

    @Override
    public void applyUpdate(
        @NotNull final Implementation<
            PipelineContextElement.Update,
            ConsumerService.State
        > implementation
    ) {
        this.pipelines.update().apply(implementation);
    }

    @Override
    public void applyClick(
        @NotNull final Implementation<
            PipelineContextElement.Click,
            ConsumerService.State
        > implementation
    ) {
        this.pipelines.click().apply(implementation);
    }

    @Override
    public void applyClear(
        @NotNull final Implementation<
            PipelineContextElement.Clear,
            ConsumerService.State
        > implementation
    ) {
        this.pipelines.clear().apply(implementation);
    }
}
