package net.infumia.frame.element;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.infumia.frame.context.ContextBase;
import net.infumia.frame.context.element.ContextElementItemClick;
import net.infumia.frame.context.element.ContextElementItemRender;
import net.infumia.frame.context.element.ContextElementItemUpdate;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.pipeline.executor.PipelineExecutorElement;
import net.infumia.frame.pipeline.executor.PipelineExecutorElementImpl;
import net.infumia.frame.service.ConsumerService;
import net.infumia.frame.util.Preconditions;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ElementItemImpl extends ElementImpl implements ElementItemRich {

    private final PipelineExecutorElement pipelines = new PipelineExecutorElementImpl(this);
    private final ElementEventHandler eventHandler = ElementEventHandlerItem.INSTANCE;
    final ItemStack item;
    final int slot;
    final Consumer<ContextElementItemClick> onClick;
    final Consumer<ContextElementItemRender> onRender;
    final Consumer<ContextElementItemUpdate> onUpdate;

    ElementItemImpl(
        @NotNull final ElementItemBuilderImpl builder,
        @NotNull final ContextBase parent
    ) {
        super(builder, parent);
        this.item = Preconditions.argumentNotNull(builder.item, "Item is not set!");
        this.slot = builder.slot;
        this.onClick = builder.onClick;
        this.onRender = builder.onRender;
        this.onUpdate = builder.onUpdate;
    }

    @NotNull
    @Override
    public ItemStack item() {
        return this.item;
    }

    @Override
    public int slot() {
        return this.slot;
    }

    @Nullable
    @Override
    public Consumer<ContextElementItemClick> onClick() {
        return this.onClick;
    }

    @Nullable
    @Override
    public Consumer<ContextElementItemRender> onRender() {
        return this.onRender;
    }

    @Nullable
    @Override
    public Consumer<ContextElementItemUpdate> onUpdate() {
        return this.onUpdate;
    }

    @NotNull
    @Override
    public ElementEventHandler eventHandler() {
        return this.eventHandler;
    }

    @Override
    public boolean containedWithin(final int slot) {
        return this.slot == slot;
    }

    @Override
    public boolean intersects(@NotNull final Element element) {
        if (!(element instanceof ElementItem)) {
            return false;
        }
        return ((ElementItem) element).slot() == this.slot;
    }

    @NotNull
    @Override
    public ElementItemBuilderRich toBuilder() {
        return new ElementItemBuilderImpl(this);
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> update() {
        Preconditions.state(
            this.parent instanceof ContextRender,
            "You cannot update the element '%s' when the parent is not a ContextRender!",
            this
        );
        return this.pipelines.executeUpdate((ContextRender) this.parent, false);
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> forceUpdate() {
        Preconditions.state(
            this.parent instanceof ContextRender,
            "You cannot update the element '%s' when the parent is not a ContextRender!",
            this
        );
        return this.pipelines.executeUpdate((ContextRender) this.parent, true);
    }

    @NotNull
    @Override
    public PipelineExecutorElement pipelines() {
        return this.pipelines;
    }
}
