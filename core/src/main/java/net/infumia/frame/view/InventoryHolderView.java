package net.infumia.frame.view;

import java.util.Objects;
import net.infumia.frame.context.view.ContextRender;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public final class InventoryHolderView implements InventoryHolder {

    private Inventory inventory;
    private ContextRender context;

    @NotNull
    public ContextRender context() {
        return Objects.requireNonNull(this.context, "context");
    }

    public void context(@NotNull final ContextRender context) {
        this.context = context;
    }

    public void inventory(@NotNull final Inventory inventory) {
        this.inventory = inventory;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return Objects.requireNonNull(this.inventory, "inventory");
    }
}
