package net.infumia.inv.core;

import net.infumia.inv.api.InventoryManager;
import net.infumia.inv.core.listener.InventoryListener;
import org.jetbrains.annotations.NotNull;

public interface InventoryManagerRich extends InventoryManager {
    @NotNull
    InventoryListener listener();
}
