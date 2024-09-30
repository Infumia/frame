package net.infumia.inv.api.state.watcher;

import net.infumia.inv.api.state.value.StateValue;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface StateWatcherAccess<T> {
    void access(@NotNull StateValue<T> value);
}
