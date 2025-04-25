package net.infumia.frame.view.creator;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface InventoryFactory {
    @NotNull
    Inventory create(
        @Nullable InventoryHolder holder,
        @NotNull InventoryType type,
        int size,
        @Nullable Object title
    );
}
