package net.infumia.inv.api.context.view;

import net.infumia.inv.api.service.Cancellable;
import net.infumia.inv.api.view.ViewContainer;
import net.infumia.inv.core.viewer.ContextualViewerRich;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ContextClick extends ContextRender, Cancellable {
    @NotNull
    InventoryClickEvent event();

    @NotNull
    ContextualViewerRich clicker();

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

    boolean layoutSlot();

    boolean layoutSlot(char character);
}
