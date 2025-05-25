package net.infumia.frame.context.element;

import net.infumia.frame.element.item.ElementItem;
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
