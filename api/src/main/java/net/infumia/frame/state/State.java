package net.infumia.frame.state;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.state.value.StateValueHostHolder;
import net.infumia.frame.state.watcher.StateWatcherAccess;
import net.infumia.frame.state.watcher.StateWatcherUpdate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface State<T> {
    @Nullable
    T get(@NotNull StateValueHostHolder host);

    @NotNull
    T getOrThrow(@NotNull StateValueHostHolder host);

    @NotNull
    CompletableFuture<@Nullable T> getWait(@NotNull StateValueHostHolder host);

    @NotNull
    CompletableFuture<T> getOtThrowWait(@NotNull StateValueHostHolder host);

    void watchAccess(@NotNull StateValueHostHolder host, @NotNull StateWatcherAccess<T> watcher);

    void watchUpdate(@NotNull StateValueHostHolder host, @NotNull StateWatcherUpdate<T> watcher);
}
