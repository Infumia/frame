package net.infumia.inv.api.context;

import net.infumia.inv.api.InventoryManager;
import net.infumia.inv.api.state.StateFactory;
import net.infumia.inv.api.typedkey.TypedKeyStorage;
import org.jetbrains.annotations.NotNull;

public interface Context {
    @NotNull
    InventoryManager manager();

    @NotNull
    TypedKeyStorage instances();

    @NotNull
    StateFactory stateFactory();
}
