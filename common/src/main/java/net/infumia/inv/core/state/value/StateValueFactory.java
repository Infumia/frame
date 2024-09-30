package net.infumia.inv.core.state.value;

import java.util.function.BiFunction;
import net.infumia.inv.api.state.value.StateValue;
import net.infumia.inv.core.context.ContextBaseRich;
import net.infumia.inv.core.state.StateRich;

@FunctionalInterface
public interface StateValueFactory<T>
    extends BiFunction<ContextBaseRich, StateRich<T>, StateValue<T>> {}
