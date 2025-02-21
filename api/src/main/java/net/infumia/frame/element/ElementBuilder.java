package net.infumia.frame.element;

import java.time.Duration;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import net.infumia.frame.context.element.ContextElementClick;
import net.infumia.frame.context.element.ContextElementRender;
import net.infumia.frame.state.State;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ElementBuilder<This extends ElementBuilder<This>> {
    @NotNull
    This cancelOnClick();

    @NotNull
    This closeOnClick();

    @NotNull
    This updateOnClick();

    @NotNull
    This cancelOnClick(boolean cancelOnClick);

    @NotNull
    This closeOnClick(boolean cancelOnClick);

    @NotNull
    This updateOnClick(boolean updateOnClick);

    @NotNull
    This interactionDelay(@Nullable Duration interactionDelay);

    @NotNull
    This onInteractionDelay(@NotNull Consumer<ContextElementClick> onInteractionDelay);

    @NotNull
    This interactionDelayKey(@NotNull Function<ContextElementClick, String> interactionDelayKey);

    @NotNull
    This updateOnStateChange(@NotNull State<?> state, @NotNull State<?>... otherStates);

    @NotNull
    This updateOnStateAccess(@NotNull State<?> state, @NotNull State<?>... otherStates);

    @NotNull
    This displayIf(@NotNull Predicate<ContextElementRender> condition);

    @NotNull
    This displayIf(@NotNull BooleanSupplier condition);

    @NotNull
    This hideIf(@NotNull Predicate<ContextElementRender> condition);

    @NotNull
    This hideIf(@NotNull BooleanSupplier condition);
}
