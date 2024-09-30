package net.infumia.inv.core.state;

import java.util.concurrent.CompletableFuture;
import net.infumia.inv.api.state.State;
import net.infumia.inv.api.state.value.StateValue;
import net.infumia.inv.api.state.value.StateValueHostHolder;
import net.infumia.inv.core.state.value.StateValueFactory;
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
