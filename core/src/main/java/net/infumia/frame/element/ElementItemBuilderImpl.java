package net.infumia.frame.element;

import java.util.function.Consumer;
import java.util.function.Function;
import net.infumia.frame.context.ContextBase;
import net.infumia.frame.context.element.ContextElementItemClick;
import net.infumia.frame.context.element.ContextElementItemRender;
import net.infumia.frame.context.element.ContextElementItemUpdate;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ElementItemBuilderImpl
    extends ElementBuilderImpl<ElementItemBuilderImpl>
    implements ElementItemBuilderRich {

    ItemStack item;
    int slot = -1;
    Consumer<ContextElementItemClick> onClick;
    Consumer<ContextElementItemRender> onRender;
    Consumer<ContextElementItemUpdate> onUpdate;

    ElementItemBuilderImpl(@NotNull final ElementItemImpl element) {
        super(element);
        this.item = element.item;
        this.slot = element.slot;
        this.onClick = element.onClick;
        this.onRender = element.onRender;
        this.onUpdate = element.onUpdate;
    }

    public ElementItemBuilderImpl() {}

    @Override
    public int slot() {
        return this.slot;
    }

    @NotNull
    @Override
    public ElementItemBuilderImpl item(@Nullable final ItemStack item) {
        this.item = item;
        return this;
    }

    @NotNull
    @Override
    public ElementItemBuilderImpl slot(final int slot) {
        this.slot = slot;
        return this;
    }

    @NotNull
    @Override
    public ElementItemBuilder onClick(@NotNull final Consumer<ContextElementItemClick> onClick) {
        this.onClick = onClick;
        return this;
    }

    @NotNull
    @Override
    public ElementItemBuilder onClick(@NotNull final Runnable onClick) {
        return this.onClick(__ -> onClick.run());
    }

    @NotNull
    @Override
    public ElementItemBuilderImpl onRender(
        @Nullable final Consumer<ContextElementItemRender> onRender
    ) {
        this.onRender = onRender;
        return this;
    }

    @NotNull
    @Override
    public ElementItemBuilder renderWith(
        @NotNull final Function<ContextElementItemRender, ItemStack> renderWith
    ) {
        return this.onRender(ctx -> ctx.modifyItem(renderWith.apply(ctx)));
    }

    @NotNull
    @Override
    public ElementItemBuilder onUpdate(
        @Nullable final Consumer<ContextElementItemUpdate> onUpdate
    ) {
        this.onUpdate = onUpdate;
        return this;
    }

    @NotNull
    @Override
    public ElementItem build(@NotNull final ContextBase parent) {
        return new ElementItemImpl(this, parent);
    }
}
