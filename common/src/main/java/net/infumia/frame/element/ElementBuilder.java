package net.infumia.frame.element;

import java.time.Duration;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.infumia.frame.context.element.ContextElementClick;
import net.infumia.frame.context.element.ContextElementRender;
import net.infumia.frame.state.State;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ElementBuilder {
    @NotNull
    ElementBuilder cancelOnClick();

    @NotNull
    ElementBuilder closeOnClick();

    @NotNull
    ElementBuilder updateOnClick();

    @NotNull
    ElementBuilder cancelOnClick(boolean cancelOnClick);

    @NotNull
    ElementBuilder closeOnClick(boolean cancelOnClick);

    @NotNull
    ElementBuilder updateOnClick(boolean updateOnClick);

    @NotNull
    ElementBuilder interactionDelay(@Nullable Duration interactionDelay);

    @NotNull
    ElementBuilder onInteractionDelay(@NotNull Consumer<ContextElementClick> onInteractionDelay);

    @NotNull
    ElementBuilder updateOnStateChange(@NotNull State<?> state, @NotNull State<?>... otherStates);

    @NotNull
    ElementBuilder updateOnStateAccess(@NotNull State<?> state, @NotNull State<?>... otherStates);

    @NotNull
    ElementBuilder displayIf(@NotNull Predicate<ContextElementRender> condition);

    @NotNull
    ElementBuilder displayIf(@NotNull BooleanSupplier condition);

    @NotNull
    ElementBuilder hideIf(@NotNull Predicate<ContextElementRender> condition);

    @NotNull
    ElementBuilder hideIf(@NotNull BooleanSupplier condition);
}
