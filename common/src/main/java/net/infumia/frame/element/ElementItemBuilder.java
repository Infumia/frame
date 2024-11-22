package net.infumia.frame.element;

import java.time.Duration;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import net.infumia.frame.context.element.ContextElementClick;
import net.infumia.frame.context.element.ContextElementItemClick;
import net.infumia.frame.context.element.ContextElementItemRender;
import net.infumia.frame.context.element.ContextElementItemUpdate;
import net.infumia.frame.context.element.ContextElementRender;
import net.infumia.frame.state.State;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ElementItemBuilder extends ElementBuilder {
    @NotNull
    ElementItemBuilder item(@NotNull ItemStack item);

    @NotNull
    ElementItemBuilder slot(int slot);

    @NotNull
    ElementItemBuilder onClick(@NotNull Consumer<ContextElementItemClick> onClick);

    @NotNull
    ElementItemBuilder onClick(@NotNull Runnable onClick);

    @NotNull
    ElementItemBuilder onRender(@NotNull Consumer<ContextElementItemRender> onRender);

    @NotNull
    ElementItemBuilder renderWith(
        @NotNull Function<ContextElementItemRender, ItemStack> renderWith
    );

    @NotNull
    ElementItemBuilder onUpdate(@NotNull Consumer<ContextElementItemUpdate> onUpdate);

    @NotNull
    @Override
    ElementItemBuilder cancelOnClick();

    @NotNull
    @Override
    ElementItemBuilder closeOnClick();

    @NotNull
    @Override
    ElementItemBuilder updateOnClick();

    @NotNull
    @Override
    ElementItemBuilder interactionDelay(@Nullable Duration interactionDelay);

    @NotNull
    @Override
    ElementItemBuilder onInteractionDelay(
        @NotNull Consumer<ContextElementClick> onInteractionDelay
    );

    @NotNull
    @Override
    ElementItemBuilder updateOnStateChange(
        @NotNull State<?> state,
        @NotNull State<?> @NotNull... otherStates
    );

    @NotNull
    @Override
    ElementItemBuilder updateOnStateAccess(
        @NotNull State<?> state,
        @NotNull State<?> @NotNull... otherStates
    );

    @NotNull
    @Override
    ElementItemBuilder displayIf(@NotNull Predicate<ContextElementRender> condition);

    @NotNull
    @Override
    ElementItemBuilder displayIf(@NotNull BooleanSupplier condition);

    @NotNull
    @Override
    ElementItemBuilder hideIf(@NotNull Predicate<ContextElementRender> condition);

    @NotNull
    @Override
    ElementItemBuilder hideIf(@NotNull BooleanSupplier condition);
}
