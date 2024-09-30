package net.infumia.inv.api.element.provider.itemstack;

import net.infumia.inv.api.context.ContextBase;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface ElementProviderItemStack {
    @NotNull
    ItemStack provideItemStack(@NotNull ContextBase ctx);
}
