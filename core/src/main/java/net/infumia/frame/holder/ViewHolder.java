package net.infumia.frame.holder;

import net.infumia.frame.context.ContextBaseRich;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public final class ViewHolder implements InventoryHolder {

    private final ContextBaseRich context;
    private Inventory inventory;

    public ViewHolder(@NotNull final ContextBaseRich context) {
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
    public ContextBaseRich context() {
        return this.context;
    }
}
