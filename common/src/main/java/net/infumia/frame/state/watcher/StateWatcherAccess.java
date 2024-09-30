package net.infumia.frame.state.watcher;

import net.infumia.frame.state.value.StateValue;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface StateWatcherAccess<T> {
    void access(@NotNull StateValue<T> value);
}
