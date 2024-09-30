package net.infumia.inv.core.view;

import net.infumia.inv.api.type.InvType;
import net.infumia.inv.api.viewer.Viewer;
import net.infumia.inv.core.InvTypeRich;
import net.infumia.inv.core.InvTypes;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ViewContainerImpl implements ViewContainerRich {

    private final Inventory inventory;
    private final InvTypeRich type;

    public ViewContainerImpl(@NotNull final Inventory inventory, @NotNull final InvTypeRich type) {
        this.inventory = inventory;
        this.type = type;
    }

    @NotNull
    @Override
    public Inventory inventory() {
        return this.inventory;
    }

    @NotNull
    @Override
    public InvType type() {
        return this.type.type();
    }

    @Override
    public int size() {
        return this.inventory.getSize();
    }

    @Override
    public int slotsCount() {
        return this.size() - 1;
    }

    @Override
    public int rowsCount() {
        return this.size() / this.columnsCount();
    }

    @Override
    public int columnsCount() {
        return this.type.columns();
    }

    @Override
    public int firstSlot() {
        return this.type == InvTypes.PLAYER ? 45 : 0;
    }

    @Override
    public int lastSlot() {
        int lastSlot = this.slotsCount();
        final int[] resultSlots = this.type.resultSlots();
        for (final int resultSlot : resultSlots) {
            if (resultSlot == lastSlot) {
                lastSlot--;
            }
        }
        return lastSlot;
    }

    @Override
    public boolean hasItem(final int slot) {
        return this.inventory.getItem(slot) != null;
    }

    @Override
    public void removeItem(final int slot) {
        this.inventory.setItem(slot, null);
    }

    @Override
    public void addItem(final int slot, @NotNull final ItemStack item) {
        this.inventory.setItem(slot, item);
    }

    @Override
    public void open(@NotNull final Viewer viewer) {
        viewer.open(this);
    }

    @Override
    public boolean isPlayerInventory() {
        return this.inventory instanceof PlayerInventory;
    }

    @NotNull
    @Override
    public InvTypeRich typeRich() {
        return this.type;
    }

    @Nullable
    @Override
    public ViewContainerRich at(final int slot) {
        return slot >= this.firstSlot() && slot <= this.lastSlot() ? this : null;
    }
}
