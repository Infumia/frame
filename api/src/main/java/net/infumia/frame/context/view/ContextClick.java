package net.infumia.frame.context.view;

import net.infumia.frame.service.Cancellable;
import net.infumia.frame.view.ViewContainer;
import net.infumia.frame.viewer.Viewer;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ContextClick extends ContextRender, Cancellable {
    @NotNull
    InventoryClickEvent event();

    @NotNull
    Viewer clicker();

    @NotNull
    ClickType clickType();

    @Nullable
    ViewContainer clickedContainer();

    int clickedSlot();

    int clickedSlotRaw();

    @NotNull
    InventoryType.SlotType clickedSlotType();

    boolean leftClick();

    boolean rightClick();

    boolean middleClick();

    boolean shiftClick();

    boolean keyboardClick();

    boolean outsideClicked();

    boolean entityContainer();

    boolean isLayoutSlot();

    boolean isLayoutSlot(char character);
}
