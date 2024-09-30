package net.infumia.inv.core.element;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.infumia.inv.api.context.element.ContextElementClick;
import net.infumia.inv.api.context.element.ContextElementItemClick;
import net.infumia.inv.api.context.element.ContextElementItemRender;
import net.infumia.inv.api.context.element.ContextElementItemUpdate;
import net.infumia.inv.api.context.element.ContextElementRender;
import net.infumia.inv.api.context.element.ContextElementUpdate;
import net.infumia.inv.api.element.Element;
import net.infumia.inv.api.element.ElementContainer;
import net.infumia.inv.api.pipeline.context.PipelineContextElement;
import net.infumia.inv.api.service.ConsumerService;
import net.infumia.inv.api.util.Preconditions;
import net.infumia.inv.core.context.element.ContextElementItemClickImpl;
import net.infumia.inv.core.context.element.ContextElementItemRenderImpl;
import net.infumia.inv.core.context.element.ContextElementItemUpdateImpl;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

final class ElementEventHandlerItem implements ElementEventHandler {

    static final ElementEventHandler INSTANCE = new ElementEventHandlerItem();

    private ElementEventHandlerItem() {}

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> handleRender(
        @NotNull final PipelineContextElement.Render ctx
    ) {
        final ContextElementRender context = ctx.context();
        final ElementItemRich element = (ElementItemRich) context.element();
        if (element.shouldRender(context)) {
            this.forceRender(element, context);
            return CompletableFuture.completedFuture(ConsumerService.State.CONTINUE);
        }
        return this.checkOverlapping(element, context);
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> handleClear(
        @NotNull final PipelineContextElement.Clear ctx
    ) {
        ctx.context().container().removeItem(((ElementItemRich) ctx.context().element()).slot());
        return CompletableFuture.completedFuture(ConsumerService.State.CONTINUE);
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> handleClick(
        @NotNull final PipelineContextElement.Click ctx
    ) {
        final ContextElementClick context = ctx.context();
        final ElementItemRich element = (ElementItemRich) context.element();
        final Consumer<ContextElementItemClick> onClick = element.onClick();
        if (onClick != null) {
            onClick.accept(new ContextElementItemClickImpl(context, element));
        }
        return CompletableFuture.completedFuture(ConsumerService.State.CONTINUE);
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> handleUpdate(
        @NotNull final PipelineContextElement.Update ctx
    ) {
        final ContextElementUpdate context = ctx.context();
        final ElementItemRich element = (ElementItemRich) context.element();
        if (!context.forced() && element.displayIf() == null && element.onRender() == null) {
            return CompletableFuture.completedFuture(ConsumerService.State.CONTINUE);
        }
        final Consumer<ContextElementItemUpdate> onUpdate = element.onUpdate();
        if (element.visible() && onUpdate != null) {
            onUpdate.accept(new ContextElementItemUpdateImpl(context, element));
        }
        if (context.cancelled()) {
            return CompletableFuture.completedFuture(ConsumerService.State.CONTINUE);
        }
        return element.pipelines().executeRender(context);
    }

    @NotNull
    private CompletableFuture<ConsumerService.State> checkOverlapping(
        @NotNull final ElementRich compareTo,
        @NotNull final ContextElementRender context
    ) {
        compareTo.visible(false);
        final Optional<ElementRich> overlappingOptional =
            this.findOverlappingElement(compareTo, context);
        if (!overlappingOptional.isPresent()) {
            return compareTo.pipelines().executeClear(context);
        }
        final ElementRich overlapping = overlappingOptional.get();
        return overlapping
            .pipelines()
            .executeRender(context)
            .thenCompose(__ -> {
                if (overlapping.visible()) {
                    return CompletableFuture.completedFuture(ConsumerService.State.CONTINUE);
                } else {
                    return compareTo.pipelines().executeClear(context);
                }
            });
    }

    @NotNull
    private Optional<ElementRich> findOverlappingElement(
        @NotNull final ElementRich compareTo,
        @NotNull final ContextElementRender context
    ) {
        for (final Element child : context.elements()) {
            final ElementRich element = (ElementRich) child;
            if (!element.visible()) {
                continue;
            }

            if (Objects.equals(element.key(), compareTo.key())) {
                continue;
            }

            if (element instanceof ElementContainer) {
                final ElementRich root = compareTo.root();
                if (root != null && Objects.equals(element.key(), root.key())) {
                    continue;
                }
                for (final Element deepChild : ((ElementContainer) element).elements()) {
                    final ElementRich deepElement = (ElementRich) deepChild;
                    if (!deepElement.visible()) {
                        continue;
                    }

                    if (Objects.equals(deepElement.key(), compareTo.key())) {
                        continue;
                    }

                    if (deepElement.intersects(compareTo)) {
                        return Optional.of(deepElement);
                    }
                }
                continue;
            }

            if (element.intersects(compareTo)) {
                return Optional.of(element);
            }
        }
        return Optional.empty();
    }

    private void forceRender(
        @NotNull final ElementItemRich element,
        @NotNull final ContextElementRender delegate
    ) {
        final ContextElementItemRender context = new ContextElementItemRenderImpl(
            delegate,
            element
        );
        final Consumer<ContextElementItemRender> onRender = element.onRender();
        if (onRender != null) {
            onRender.accept(context);
        }
        final ItemStack modifiedItem = context.modifiedItem();
        final int modifiedSlot = context.modifiedSlot();
        // TODO: portlek, More detailed message.
        Preconditions.state(modifiedSlot != -1, "Element's '%s' slot is not set!", element);
        delegate.container().addItem(modifiedSlot, modifiedItem);
        element.visible(true);
    }
}
