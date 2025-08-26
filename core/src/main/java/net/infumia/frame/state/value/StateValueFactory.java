package net.infumia.frame.state.value;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import net.infumia.frame.context.ContextBase;
import net.infumia.frame.state.State;

@FunctionalInterface
public interface StateValueFactory<T>
    extends BiFunction<ContextBase, State<T>, CompletableFuture<StateValue<T>>> {}
