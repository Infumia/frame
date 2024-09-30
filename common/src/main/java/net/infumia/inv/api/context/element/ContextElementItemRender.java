package net.infumia.inv.api.context.element;

import net.infumia.inv.api.element.ElementItem;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface ContextElementItemRender extends ContextElementRender {
    @NotNull
    @Override
    ElementItem element();

    int modifiedSlot();

    void modifySlot(int slot);

    @NotNull
    ItemStack modifiedItem();

    void modifyItem(@NotNull ItemStack newItem);
}
