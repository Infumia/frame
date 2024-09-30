package net.infumia.frame.view.creator;

import net.infumia.frame.util.PaperLib;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InventoryCreatorBukkit implements InventoryCreator {

    public static final InventoryCreator INSTANCE = new InventoryCreatorBukkit();

    @NotNull
    public static InventoryCreator bukkitOrPaper() {
        if (PaperLib.isPaper() && PaperLib.isVersion(16)) {
            return InventoryCreatorPaper.INSTANCE;
        }
        return InventoryCreatorBukkit.INSTANCE;
    }

    @NotNull
    @Override
    public Inventory create(
        @Nullable final InventoryHolder holder,
        @NotNull final InventoryType type,
        final int size,
        @Nullable final Object title
    ) {
        @NotNull
        final String titleAsText;
        if (title instanceof String && !((String) title).isEmpty()) {
            titleAsText = (String) title;
        } else if (title != null) {
            throw new IllegalArgumentException(
                String.format("Title must be only either String or null '%s'", title)
            );
        } else {
            titleAsText = type.getDefaultTitle();
        }

        return size == 0
            ? Bukkit.createInventory(holder, type, titleAsText)
            : Bukkit.createInventory(holder, size, titleAsText);
    }

    protected InventoryCreatorBukkit() {}
}