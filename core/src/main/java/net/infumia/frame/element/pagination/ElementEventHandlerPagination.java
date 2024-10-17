package net.infumia.frame.element.pagination;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.infumia.frame.context.element.ContextElementClear;
import net.infumia.frame.context.element.ContextElementClick;
import net.infumia.frame.context.element.ContextElementRender;
import net.infumia.frame.context.element.ContextElementUpdate;
import net.infumia.frame.element.Element;
import net.infumia.frame.element.ElementEventHandler;
import net.infumia.frame.element.ElementRich;
import net.infumia.frame.pipeline.context.PipelineContextElement;
import net.infumia.frame.service.ConsumerService;
import net.infumia.frame.state.StateRich;
import org.jetbrains.annotations.NotNull;

final class ElementEventHandlerPagination implements ElementEventHandler {

    static final ElementEventHandler INSTANCE = new ElementEventHandlerPagination();

    private ElementEventHandlerPagination() {}

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> handleRender(
        @NotNull final PipelineContextElement.Render ctx
    ) {
        final ContextElementRender context = ctx.context();
        final ElementPaginationRich<?> pagination = (ElementPaginationRich<?>) context.element();
        if (pagination.initialized() && !pagination.pageWasChanged()) {
            pagination.visible(true);
            return this.renderChild(context, pagination);
        }
        if (!pagination.initialized()) {
            pagination.updatePageSize(context);
        }
        return pagination
            .loadCurrentPage(context, context.forced())
            .thenCompose(__ -> {
                pagination.visible(true);
                pagination.initialized(true);
                return this.renderChild(context, pagination)
                    .thenCompose(s ->
                        ((StateRich<ElementPagination>) pagination.associated()).manualUpdateWait(
                                context
                            )
                    )
                    .thenApply(v -> ConsumerService.State.CONTINUE);
            });
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> handleClear(
        @NotNull final PipelineContextElement.Clear ctx
    ) {
        final ContextElementClear context = ctx.context();
        final ElementPaginationRich<?> pagination = (ElementPaginationRich<?>) context.element();
        if (!pagination.pageWasChanged()) {
            final List<Element> elements = pagination.elements();
            final CompletableFuture<?>[] futures = new CompletableFuture<?>[elements.size()];
            for (int i = 0; i < futures.length; i++) {
                futures[i] = ((ElementRich) elements.get(i)).pipelines().executeClear(context);
            }
            return CompletableFuture.allOf(futures).thenApply(__ -> ConsumerService.State.CONTINUE);
        }
        final Collection<Element> elements = pagination.modifiableElements();
        final Collection<CompletableFuture<?>> futures = new ArrayList<>();
        final Iterator<Element> iterator = elements.iterator();
        while (iterator.hasNext()) {
            final Element element = iterator.next();
            futures.add(((ElementRich) element).pipelines().executeClear(context));
            iterator.remove();
        }
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).thenApply(__ ->
            ConsumerService.State.CONTINUE
        );
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> handleClick(
        @NotNull final PipelineContextElement.Click ctx
    ) {
        final ContextElementClick context = ctx.context();
        final ElementPaginationRich<?> pagination = (ElementPaginationRich<?>) context.element();
        if (pagination.pageWasChanged() || !pagination.visible()) {
            ctx.cancelled(true);
            context.cancelled(true);
            return CompletableFuture.completedFuture(ConsumerService.State.CONTINUE);
        }
        for (final Element child : pagination.elements()) {
            final ElementRich element = (ElementRich) child;
            if (!element.containedWithin(context.clickedSlotRaw()) || !element.visible()) {
                continue;
            }
            return element.pipelines().executeClick(context);
        }
        return CompletableFuture.completedFuture(ConsumerService.State.CONTINUE);
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> handleUpdate(
        @NotNull final PipelineContextElement.Update ctx
    ) {
        final ContextElementUpdate context = ctx.context();
        final ElementPaginationRich<?> pagination = (ElementPaginationRich<?>) context.element();
        if (pagination.pageWasChanged() || context.forced()) {
            return pagination
                .pipelines()
                .executeClear(context)
                .thenCompose(__ -> {
                    pagination.clearElements();
                    return pagination.pipelines().executeRender(context, context.forced());
                })
                .thenApply(__ -> {
                    pagination.pageWasChanged(false);
                    return ConsumerService.State.CONTINUE;
                });
        }
        if (!pagination.visible()) {
            return CompletableFuture.completedFuture(ConsumerService.State.CONTINUE);
        }
        final List<Element> elements = pagination.elements();
        final CompletableFuture<?>[] futures = new CompletableFuture<?>[elements.size()];
        for (int i = 0; i < futures.length; i++) {
            final ElementRich element = (ElementRich) elements.get(i);
            if (context.forced()) {
                futures[i] = element.forceUpdate();
            } else {
                futures[i] = element.update();
            }
        }
        return CompletableFuture.allOf(futures).thenApply(__ -> ConsumerService.State.CONTINUE);
    }

    @NotNull
    private CompletableFuture<ConsumerService.State> renderChild(
        @NotNull final ContextElementRender context,
        @NotNull final ElementPaginationRich<?> pagination
    ) {
        final List<Element> elements = pagination.elements();
        final CompletableFuture<?>[] futures = new CompletableFuture[elements.size()];
        for (int i = 0; i < futures.length; i++) {
            final ElementRich element = (ElementRich) elements.get(i);
            futures[i] = element.pipelines().executeRender(context, context.forced());
        }
        return CompletableFuture.allOf(futures).thenApply(__ -> ConsumerService.State.CONTINUE);
    }
}
