package net.infumia.inv.core;

import net.infumia.inv.api.InventoryManager;
import net.infumia.inv.api.InventoryManagerFactory;
import net.infumia.inv.api.logger.Logger;
import net.infumia.inv.core.logger.PluginLogger;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public final class InventoryManagerFactoryImpl implements InventoryManagerFactory {

    public static final InventoryManagerFactory INSTANCE = new InventoryManagerFactoryImpl();

    private InventoryManagerFactoryImpl() {}

    @NotNull
    @Override
    public InventoryManager create(
        @NotNull final Plugin plugin,
        @NotNull final Logger logger,
        final boolean unregisterOnDisable
    ) {
        return new InventoryManagerImpl(plugin, logger, unregisterOnDisable);
    }

    @NotNull
    @Override
    public InventoryManager create(@NotNull final Plugin plugin, @NotNull final Logger logger) {
        return this.create(plugin, logger, true);
    }

    @NotNull
    @Override
    public InventoryManager create(
        @NotNull final Plugin plugin,
        final boolean unregisterOnDisable
    ) {
        return this.create(plugin, new PluginLogger(plugin), unregisterOnDisable);
    }

    @NotNull
    @Override
    public InventoryManager create(@NotNull final Plugin plugin) {
        return this.create(plugin, true);
    }
}
