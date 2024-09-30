package net.infumia.frame.context.view;

import net.infumia.frame.slot.LayoutSlot;
import net.infumia.frame.view.ViewContainer;
import net.infumia.frame.viewer.ContextualViewer;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ContextClickImpl extends ContextRenderImpl implements ContextClick {

    private final ContextualViewer clicker;
    private final InventoryClickEvent event;

    public ContextClickImpl(
        @NotNull final ContextualViewer clicker,
        @NotNull final InventoryClickEvent event
    ) {
        super(clicker.context());
        this.clicker = clicker;
        this.event = event;
    }

    public ContextClickImpl(@NotNull final ContextClick context) {
        this(context.clicker(), context.event());
    }

    @NotNull
    @Override
    public InventoryClickEvent event() {
        return this.event;
    }

    @NotNull
    @Override
    public ContextualViewer clicker() {
        return this.clicker;
    }

    @NotNull
    @Override
    public ClickType clickType() {
        return this.event.getClick();
    }

    @Nullable
    @Override
    public ViewContainer clickedContainer() {
        return this.container().at(this.clickedSlot());
    }

    @Override
    public int clickedSlot() {
        return this.event.getSlot();
    }

    @Override
    public int clickedSlotRaw() {
        return this.event.getRawSlot();
    }

    @NotNull
    @Override
    public InventoryType.SlotType clickedSlotType() {
        return this.event.getSlotType();
    }

    @Override
    public boolean leftClick() {
        return this.event.isLeftClick();
    }

    @Override
    public boolean rightClick() {
        return this.event.isRightClick();
    }

    @Override
    public boolean middleClick() {
        return this.clickType() == ClickType.MIDDLE;
    }

    @Override
    public boolean shiftClick() {
        return this.event.isShiftClick();
    }

    @Override
    public boolean keyboardClick() {
        return this.clickType().isKeyboardClick();
    }

    @Override
    public boolean outsideClicked() {
        return this.clickedSlotType() == InventoryType.SlotType.OUTSIDE;
    }

    @Override
    public boolean entityContainer() {
        return this.event.getClickedInventory() instanceof PlayerInventory;
    }

    @Override
    public boolean layoutSlot() {
        return this.layouts().values().stream().anyMatch(slot -> slot.contains(this.clickedSlot()));
    }

    @Override
    public boolean layoutSlot(final char character) {
        final LayoutSlot slot = this.layouts().get(character);
        if (slot == null) {
            return false;
        }
        return slot.contains(this.clickedSlot());
    }

    @Override
    public boolean cancelled() {
        return this.event.isCancelled();
    }

    @Override
    public void cancelled(final boolean cancelled) {
        this.event.setCancelled(cancelled);
    }
}
