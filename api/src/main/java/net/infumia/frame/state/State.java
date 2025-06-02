package net.infumia.frame.state;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.state.value.StateValueHostHolder;
import net.infumia.frame.state.watcher.StateWatcherAccess;
import net.infumia.frame.state.watcher.StateWatcherUpdate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface State<T> {
    @Nullable
    T get(@NotNull StateValueHostHolder host);

    @NotNull
    T getOrThrow(@NotNull StateValueHostHolder host);

    @Contract("_, null -> null; _, !null -> !null")
    T getOrDefault(@NotNull StateValueHostHolder host, @Nullable T defaultValue);

    @NotNull
    CompletableFuture<@Nullable T> getWait(@NotNull StateValueHostHolder host);

    @NotNull
    CompletableFuture<T> getOrThrowWait(@NotNull StateValueHostHolder host);

    @NotNull
    CompletableFuture<T> getOrDefaultWait(
        @NotNull StateValueHostHolder host,
        @Nullable T defaultValue
    );

    void watchAccess(@NotNull StateValueHostHolder host, @NotNull StateWatcherAccess<T> watcher);

    void watchUpdate(@NotNull StateValueHostHolder host, @NotNull StateWatcherUpdate<T> watcher);
}
