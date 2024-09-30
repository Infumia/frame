package net.infumia.frame.state.value;

import java.util.function.BiFunction;
import net.infumia.frame.context.ContextBaseRich;
import net.infumia.frame.state.StateRich;

@FunctionalInterface
public interface StateValueFactory<T>
    extends BiFunction<ContextBaseRich, StateRich<T>, StateValue<T>> {}
