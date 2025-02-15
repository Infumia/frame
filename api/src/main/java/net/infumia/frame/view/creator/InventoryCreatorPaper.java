package net.infumia.frame.view.creator;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class InventoryCreatorPaper extends InventoryCreatorBukkit {

    public static final InventoryCreator INSTANCE = new InventoryCreatorPaper();

    @NotNull
    @Override
    public Inventory create(
        @Nullable final InventoryHolder holder,
        @NotNull final InventoryType type,
        final int size,
        @Nullable final Object title
    ) {
        if (title instanceof String) {
            return super.create(holder, type, size, title);
        }
        final Component finalTitle;
        if (title instanceof Component) {
            finalTitle = (Component) title;
        } else if (title != null) {
            throw new IllegalArgumentException(
                String.format("Title must be only either Component or null '%s'", title)
            );
        } else {
            finalTitle = type.defaultTitle();
        }
        return size == 0
            ? Bukkit.createInventory(holder, type, finalTitle)
            : Bukkit.createInventory(holder, size, finalTitle);
    }
}
