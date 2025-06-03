package net.infumia.frame.view;

import net.infumia.frame.InvTypeRich;
import net.infumia.frame.InvTypes;
import net.infumia.frame.type.InvType;
import net.infumia.frame.viewer.Viewer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ViewContainerImpl implements ViewContainerRich {

    private final InventoryHolderFrame inventoryHolder;
    private final InvTypeRich type;

    public ViewContainerImpl(
        @NotNull final InventoryHolderFrame inventoryHolder,
        @NotNull final InvTypeRich type
    ) {
        this.inventoryHolder = inventoryHolder;
        this.type = type;
    }

    @NotNull
    @Override
    public InventoryHolderFrame inventoryHolder() {
        return this.inventoryHolder;
    }

    @NotNull
    @Override
    public InvType type() {
        return this.type.type();
    }

    @Override
    public int size() {
        return this.inventoryHolder.getInventory().getSize();
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
        return this.inventoryHolder.getInventory().getItem(slot) != null;
    }

    @Override
    public void removeItem(final int slot) {
        this.inventoryHolder.getInventory().setItem(slot, null);
    }

    @Override
    public void addItem(final int slot, @NotNull final ItemStack item) {
        this.inventoryHolder.getInventory().setItem(slot, item);
    }

    @Override
    public void open(@NotNull final Viewer viewer) {
        viewer.open(this);
    }

    @Override
    public boolean isPlayerInventory() {
        return this.inventoryHolder.getInventory() instanceof PlayerInventory;
    }

    @Nullable
    @Override
    public ViewContainer at(final int slot) {
        return slot >= this.firstSlot() && slot <= this.lastSlot() ? this : null;
    }

    @NotNull
    @Override
    public InvTypeRich typeRich() {
        return this.type;
    }
}
