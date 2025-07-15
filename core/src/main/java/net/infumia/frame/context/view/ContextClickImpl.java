package net.infumia.frame.context.view;

import net.infumia.frame.view.ViewContainer;
import net.infumia.frame.viewer.Viewer;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ContextClickImpl extends ContextRenderImpl implements ContextClick {

    private final Viewer clicker;
    private final InventoryClickEvent event;

    public ContextClickImpl(
        @NotNull final ContextRender ctx,
        @NotNull final Viewer clicker,
        @NotNull final InventoryClickEvent event
    ) {
        super(ctx);
        this.clicker = clicker;
        this.event = event;
    }

    public ContextClickImpl(@NotNull final ContextClick context) {
        this(context, context.clicker(), context.event());
    }

    @NotNull
    @Override
    public InventoryClickEvent event() {
        return this.event;
    }

    @NotNull
    @Override
    public Viewer clicker() {
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
    public boolean isLayoutSlot() {
        return this.layouts()
            .stream()
            .anyMatch(slot -> slot.contains(this.clickedSlot()));
    }

    @Override
    public boolean isLayoutSlot(final char character) {
        return this.layouts()
            .stream()
            .filter(slot -> slot.character() == character)
            .findFirst()
            .map(slot -> slot.contains(this.clickedSlotRaw()))
            .orElse(false);
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
