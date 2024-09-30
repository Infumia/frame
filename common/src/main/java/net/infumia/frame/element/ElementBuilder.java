package net.infumia.frame.element;

import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.state.State;
import org.jetbrains.annotations.NotNull;

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
    ElementBuilder updateOnStateChange(@NotNull State<?> state, @NotNull State<?>... otherStates);

    @NotNull
    ElementBuilder updateOnStateAccess(@NotNull State<?> state, @NotNull State<?>... otherStates);

    @NotNull
    ElementBuilder displayIf(@NotNull Predicate<ContextRender> condition);

    @NotNull
    ElementBuilder displayIf(@NotNull BooleanSupplier condition);

    @NotNull
    ElementBuilder hideIf(@NotNull Predicate<ContextRender> condition);

    @NotNull
    ElementBuilder hideIf(@NotNull BooleanSupplier condition);
}
