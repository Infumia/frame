package net.infumia.frame.element.item;

import java.time.Duration;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import net.infumia.frame.context.ContextBase;
import net.infumia.frame.context.element.ContextElementClick;
import net.infumia.frame.context.element.ContextElementItemClick;
import net.infumia.frame.context.element.ContextElementItemRender;
import net.infumia.frame.context.element.ContextElementItemUpdate;
import net.infumia.frame.context.element.ContextElementRender;
import net.infumia.frame.element.ElementBuilderImpl;
import net.infumia.frame.state.State;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ElementItemBuilderImpl
    extends ElementBuilderImpl
    implements ElementItemBuilderRich {

    ItemStack item;
    int slot = -1;
    Consumer<ContextElementItemClick> onClick;
    Consumer<ContextElementItemRender> onRender;
    Consumer<ContextElementItemUpdate> onUpdate;

    public ElementItemBuilderImpl() {}

    @Override
    public int slot() {
        return this.slot;
    }

    @NotNull
    @Override
    public ElementItemBuilder item(@Nullable final ItemStack item) {
        this.item = item;
        return this;
    }

    @NotNull
    @Override
    public ElementItemBuilder slot(final int slot) {
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
    public ElementItemBuilder onRender(
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
    public ElementItemBuilder hideIf(@NotNull final BooleanSupplier condition) {
        super.hideIf(condition);
        return this;
    }

    @NotNull
    @Override
    public ElementItemBuilder hideIf(@NotNull final Predicate<ContextElementRender> condition) {
        super.hideIf(condition);
        return this;
    }

    @NotNull
    @Override
    public ElementItemBuilder displayIf(@NotNull final BooleanSupplier condition) {
        super.displayIf(condition);
        return this;
    }

    @NotNull
    @Override
    public ElementItemBuilder displayIf(@NotNull final Predicate<ContextElementRender> condition) {
        super.displayIf(condition);
        return this;
    }

    @NotNull
    @Override
    public ElementItemBuilder updateOnStateAccess(
        @NotNull final State<?> state,
        @NotNull final State<?> @NotNull... otherStates
    ) {
        super.updateOnStateAccess(state, otherStates);
        return this;
    }

    @NotNull
    @Override
    public ElementItemBuilder updateOnStateChange(
        @NotNull final State<?> state,
        @NotNull final State<?> @NotNull... otherStates
    ) {
        super.updateOnStateChange(state, otherStates);
        return this;
    }

    @NotNull
    @Override
    public ElementItemBuilder interactionDelayKey(
        @NotNull final Function<ContextElementClick, String> interactionDelayKey
    ) {
        super.interactionDelayKey(interactionDelayKey);
        return this;
    }

    @NotNull
    @Override
    public ElementItemBuilder onInteractionDelay(
        @NotNull final Consumer<ContextElementClick> onInteractionDelay
    ) {
        super.onInteractionDelay(onInteractionDelay);
        return this;
    }

    @NotNull
    @Override
    public ElementItemBuilder interactionDelay(@Nullable final Duration interactionDelay) {
        super.interactionDelay(interactionDelay);
        return this;
    }

    @NotNull
    @Override
    public ElementItemBuilder updateOnClick(final boolean updateOnClick) {
        super.updateOnClick(updateOnClick);
        return this;
    }

    @NotNull
    @Override
    public ElementItemBuilder closeOnClick(final boolean cancelOnClick) {
        super.closeOnClick(cancelOnClick);
        return this;
    }

    @NotNull
    @Override
    public ElementItemBuilder cancelOnClick(final boolean cancelOnClick) {
        super.cancelOnClick(cancelOnClick);
        return this;
    }

    @NotNull
    @Override
    public ElementItemBuilder updateOnClick() {
        super.updateOnClick();
        return this;
    }

    @NotNull
    @Override
    public ElementItemBuilder closeOnClick() {
        super.closeOnClick();
        return this;
    }

    @NotNull
    @Override
    public ElementItemBuilder cancelOnClick() {
        super.cancelOnClick();
        return this;
    }

    @NotNull
    @Override
    public ElementItem build(@NotNull final ContextBase parent) {
        return new ElementItemImpl(this, parent);
    }
}
