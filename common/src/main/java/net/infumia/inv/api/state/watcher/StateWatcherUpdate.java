package net.infumia.inv.api.state.watcher;

import net.infumia.inv.api.state.value.StateUpdate;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface StateWatcherUpdate<T> {
    void update(@NotNull StateUpdate<T> update);
}
