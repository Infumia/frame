package net.infumia.inv.core.context;

import net.infumia.inv.api.context.Context;
import net.infumia.inv.core.InventoryManagerRich;
import net.infumia.inv.core.state.StateRegistry;
import org.jetbrains.annotations.NotNull;

public interface ContextRich extends Context {
    @NotNull
    @Override
    InventoryManagerRich manager();

    @NotNull
    StateRegistry stateRegistry();
}
