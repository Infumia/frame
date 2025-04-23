package net.infumia.frame.holder;

import net.infumia.frame.context.ContextBase;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public final class ViewHolder implements InventoryHolder {

    private final ContextBase context;
    private Inventory inventory;

    public ViewHolder(@NotNull final ContextBase context) {
        this.context = context;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    public void init(@NotNull final Inventory inventory) {
        this.inventory = inventory;
    }

    @NotNull
    public ContextBase context() {
        return this.context;
    }
}
