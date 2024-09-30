package net.infumia.frame.state;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.state.value.StateValue;
import net.infumia.frame.state.value.StateValueFactory;
import net.infumia.frame.state.value.StateValueHostHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface StateRich<T> extends State<T> {
    long id();

    @NotNull
    StateValueFactory<T> valueFactory();

    @Nullable
    StateValue<T> manualUpdate(@NotNull StateValueHostHolder host);

    @NotNull
    CompletableFuture<StateValue<T>> manualUpdateWait(@NotNull StateValueHostHolder host);
}
