package net.infumia.inv.core.pipeline.executor;

import java.util.concurrent.CompletableFuture;
import net.infumia.inv.api.context.view.ContextClick;
import net.infumia.inv.api.context.view.ContextRender;
import net.infumia.inv.api.pipeline.context.PipelineContextElement;
import net.infumia.inv.api.pipeline.executor.PipelineExecutorElement;
import net.infumia.inv.api.service.ConsumerService;
import net.infumia.inv.api.service.Implementation;
import net.infumia.inv.core.context.element.ContextElementClearImpl;
import net.infumia.inv.core.context.element.ContextElementClickImpl;
import net.infumia.inv.core.context.element.ContextElementRenderImpl;
import net.infumia.inv.core.context.element.ContextElementUpdateImpl;
import net.infumia.inv.core.context.view.ContextRenderRich;
import net.infumia.inv.core.element.ElementRich;
import net.infumia.inv.core.pipeline.context.PipelineContextElements;
import net.infumia.inv.core.pipeline.holder.PipelineHolderElement;
import org.jetbrains.annotations.NotNull;

public final class PipelineExecutorElementImpl implements PipelineExecutorElement {

    private final PipelineHolderElement pipelines = PipelineHolderElement.BASE.createNew();
    private final ElementRich element;

    public PipelineExecutorElementImpl(@NotNull final ElementRich element) {
        this.element = element;
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> executeRender(
        @NotNull final ContextRender context
    ) {
        return this.pipelines.render()
            .completeWith(
                new PipelineContextElements.Render(
                    new ContextElementRenderImpl((ContextRenderRich) context, this.element)
                )
            );
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> executeUpdate(
        @NotNull final ContextRender context,
        final boolean forced
    ) {
        return this.pipelines.update()
            .completeWith(
                new PipelineContextElements.Update(
                    new ContextElementUpdateImpl((ContextRenderRich) context, this.element, forced)
                )
            );
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> executeClick(
        @NotNull final ContextClick context
    ) {
        return this.pipelines.click()
            .completeWith(
                new PipelineContextElements.Click(
                    new ContextElementClickImpl(context, this.element)
                )
            );
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> executeClear(
        @NotNull final ContextRender context
    ) {
        return this.pipelines.clear()
            .completeWith(
                new PipelineContextElements.Clear(
                    new ContextElementClearImpl((ContextRenderRich) context, this.element)
                )
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
