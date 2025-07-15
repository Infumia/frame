package net.infumia.frame.element.item;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.infumia.frame.Preconditions;
import net.infumia.frame.context.element.ContextElementClick;
import net.infumia.frame.context.element.ContextElementItemClick;
import net.infumia.frame.context.element.ContextElementItemClickImpl;
import net.infumia.frame.context.element.ContextElementItemRender;
import net.infumia.frame.context.element.ContextElementItemRenderImpl;
import net.infumia.frame.context.element.ContextElementItemUpdate;
import net.infumia.frame.context.element.ContextElementItemUpdateImpl;
import net.infumia.frame.context.element.ContextElementRender;
import net.infumia.frame.context.element.ContextElementUpdate;
import net.infumia.frame.element.Element;
import net.infumia.frame.element.ElementContainer;
import net.infumia.frame.element.ElementEventHandler;
import net.infumia.frame.element.ElementRich;
import net.infumia.frame.pipeline.context.PipelineContextElement;
import net.infumia.frame.service.ConsumerService;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

final class ElementEventHandlerItem implements ElementEventHandler {

    static final ElementEventHandler INSTANCE = new ElementEventHandlerItem();

    private ElementEventHandlerItem() {}

    @NotNull
    @Override
    public CompletableFuture<?> handleRender(@NotNull final PipelineContextElement.Render ctx) {
        final ContextElementRender context = ctx.context();
        final ElementItemRich element = (ElementItemRich) context.element();
        if (element.shouldRender(context)) {
            this.renderInternally(element, context);
            return CompletableFuture.completedFuture(null);
        }
        element.visible(false);
        return this.checkOverlapping(element, context);
    }

    @NotNull
    @Override
    public CompletableFuture<?> handleClear(@NotNull final PipelineContextElement.Clear ctx) {
        ctx.context().container().removeItem(((ElementItemRich) ctx.context().element()).slot());
        return CompletableFuture.completedFuture(null);
    }

    @NotNull
    @Override
    public CompletableFuture<?> handleClick(@NotNull final PipelineContextElement.Click ctx) {
        final ContextElementClick context = ctx.context();
        final ElementItemRich element = (ElementItemRich) context.element();
        final Consumer<ContextElementItemClick> onClick = element.onClick();
        if (onClick != null) {
            onClick.accept(new ContextElementItemClickImpl(context, element));
        }
        return CompletableFuture.completedFuture(null);
    }

    @NotNull
    @Override
    public CompletableFuture<?> handleUpdate(@NotNull final PipelineContextElement.Update ctx) {
        final ContextElementUpdate context = ctx.context();
        final ElementItemRich element = (ElementItemRich) context.element();
        if (!context.forced() && element.displayIf() == null && element.onRender() == null) {
            return CompletableFuture.completedFuture(null);
        }
        final Consumer<ContextElementItemUpdate> onUpdate = element.onUpdate();
        if (element.visible() && onUpdate != null) {
            onUpdate.accept(new ContextElementItemUpdateImpl(context, element));
        }
        if (context.cancelled()) {
            return CompletableFuture.completedFuture(null);
        }
        return element.pipelines().executeRender(context, context.forced());
    }

    @NotNull
    private CompletableFuture<ConsumerService.State> checkOverlapping(
        @NotNull final ElementRich compareTo,
        @NotNull final ContextElementRender context
    ) {
        final Optional<ElementRich> overlappingOptional = this.findOverlappingElement(
            compareTo,
            context
        );
        if (!overlappingOptional.isPresent()) {
            return compareTo.pipelines().executeClear(context);
        }
        final ElementRich overlapping = overlappingOptional.get();
        return overlapping
            .pipelines()
            .executeRender(context, false)
            .thenCompose(__ -> {
                if (overlapping.visible()) {
                    return CompletableFuture.completedFuture(null);
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

            if (element instanceof ElementContainer) {
                final ElementRich root = (ElementRich) compareTo.root();
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

            if (Objects.equals(element.key(), compareTo.key())) {
                continue;
            }

            if (element.intersects(compareTo)) {
                return Optional.of(element);
            }
        }
        return Optional.empty();
    }

    private void renderInternally(
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
