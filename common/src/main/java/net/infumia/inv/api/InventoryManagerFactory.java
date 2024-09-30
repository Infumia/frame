package net.infumia.inv.api;

import net.infumia.inv.api.logger.Logger;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface InventoryManagerFactory {
    @NotNull
    InventoryManager create(
        @NotNull Plugin plugin,
        @NotNull Logger logger,
        boolean unregisterOnDisable
    );

    @NotNull
    InventoryManager create(@NotNull Plugin plugin, @NotNull Logger logger);

    @NotNull
    InventoryManager create(@NotNull Plugin plugin, boolean unregisterOnDisable);

    @NotNull
    InventoryManager create(@NotNull Plugin plugin);
}
