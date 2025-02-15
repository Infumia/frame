package net.infumia.frame.state.watcher;

import net.infumia.frame.state.value.StateUpdate;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface StateWatcherUpdate<T> {
    void update(@NotNull StateUpdate<T> update);
}
