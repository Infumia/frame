package net.infumia.frame.element;

import java.util.Collection;
import java.util.function.Predicate;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.state.State;
import org.jetbrains.annotations.Nullable;

public interface Element {
    boolean cancelOnClick();

    boolean closeOnClick();

    boolean updateOnClick();

    @Nullable
    Predicate<ContextRender> displayIf();

    @Nullable
    Collection<State<?>> updateOnStateChange();

    @Nullable
    Collection<State<?>> updateOnStateAccess();
}