package net.infumia.frame.view;

import net.infumia.frame.type.InvType;
import net.infumia.frame.viewer.Viewer;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ViewContainer {
    @NotNull
    InventoryHolder inventoryHolder();

    @NotNull
    InvType type();

    int size();

    int slotsCount();

    int rowsCount();

    int columnsCount();

    int firstSlot();

    int lastSlot();

    boolean hasItem(int slot);

    void removeItem(int slot);

    void addItem(int slot, @NotNull ItemStack item);

    void open(@NotNull Viewer viewer);

    boolean isPlayerInventory();

    @Nullable
    ViewContainer at(int slot);
}
