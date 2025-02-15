package net.infumia.frame.state;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.state.value.StateValue;
import net.infumia.frame.state.value.StateValueHostHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface StateMutable<T> extends State<T> {
    @Nullable
    StateValue<T> set(@NotNull StateValueHostHolder host, @Nullable T value);

    @NotNull
    CompletableFuture<@Nullable StateValue<T>> setWait(
        @NotNull StateValueHostHolder host,
        @Nullable T value
    );
}
