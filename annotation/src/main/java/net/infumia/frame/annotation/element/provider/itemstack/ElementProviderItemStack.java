package net.infumia.frame.element.provider.itemstack;

import net.infumia.frame.context.ContextBase;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface ElementProviderItemStack {
    @NotNull
    ItemStack provideItemStack(@NotNull ContextBase ctx);
}
