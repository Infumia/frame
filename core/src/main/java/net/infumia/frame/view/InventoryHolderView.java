package net.infumia.frame.view;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public final class InventoryHolderView implements InventoryHolder {
    private Inventory inventory;

    public void setInventory(@NotNull final Inventory inventory) {
        this.inventory = inventory;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return this.inventory;
    }
}
