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

public interface ElementBuilderChain<This extends ElementBuilderChain<This>>
    extends ElementBuilder {
    @NotNull
    @Override
    This cancelOnClick();

    @NotNull
    @Override
    This closeOnClick();

    @NotNull
    @Override
    This updateOnClick();

    @NotNull
    @Override
    This cancelOnClick(boolean cancelOnClick);

    @NotNull
    @Override
    This closeOnClick(boolean cancelOnClick);

    @NotNull
    @Override
    This updateOnClick(boolean updateOnClick);

    @NotNull
    @Override
    This interactionDelay(@Nullable Duration interactionDelay);

    @NotNull
    @Override
    This onInteractionDelay(@NotNull Consumer<ContextElementClick> onInteractionDelay);

    @NotNull
    @Override
    This interactionDelayKey(@NotNull Function<ContextElementClick, String> interactionDelayKey);

    @NotNull
    @Override
    This updateOnStateChange(@NotNull State<?> state, @NotNull State<?>... otherStates);

    @NotNull
    @Override
    This updateOnStateAccess(@NotNull State<?> state, @NotNull State<?>... otherStates);

    @NotNull
    @Override
    This displayIf(@NotNull Predicate<ContextElementRender> condition);

    @NotNull
    @Override
    This displayIf(@NotNull BooleanSupplier condition);

    @NotNull
    @Override
    This hideIf(@NotNull Predicate<ContextElementRender> condition);

    @NotNull
    @Override
    This hideIf(@NotNull BooleanSupplier condition);
}
