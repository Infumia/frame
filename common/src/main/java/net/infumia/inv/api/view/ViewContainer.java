package net.infumia.inv.api.view;

import net.infumia.inv.api.type.InvType;
import net.infumia.inv.api.viewer.Viewer;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ViewContainer {
    @NotNull
    Inventory inventory();

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
