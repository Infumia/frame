package net.infumia.frame.element;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import net.infumia.frame.context.element.ContextElementRender;
import net.infumia.frame.service.ConsumerService;
import net.infumia.frame.state.State;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Element {
    boolean cancelOnClick();

    boolean closeOnClick();

    boolean updateOnClick();

    @Nullable
    Predicate<ContextElementRender> displayIf();

    @Nullable
    Collection<State<?>> updateOnStateChange();

    @Nullable
    Collection<State<?>> updateOnStateAccess();

    @NotNull
    CompletableFuture<ConsumerService.State> update();

    @NotNull
    CompletableFuture<ConsumerService.State> forceUpdate();
}
