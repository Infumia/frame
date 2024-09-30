package net.infumia.inv.core.context.element;

import net.infumia.inv.api.context.element.ContextElementItemRender;
import net.infumia.inv.api.context.element.ContextElementRender;
import net.infumia.inv.core.element.ElementItemRich;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class ContextElementItemRenderImpl
    extends ContextElementRenderImpl
    implements ContextElementItemRender {

    private final ElementItemRich element;
    private int modifiedSlot;
    private ItemStack modifiedItem;

    public ContextElementItemRenderImpl(
        @NotNull final ContextElementRender context,
        @NotNull final ElementItemRich element
    ) {
        super(context);
        this.element = element;
        this.modifiedSlot = element.slot();
        this.modifiedItem = element.item();
    }

    @NotNull
    @Override
    public ElementItemRich element() {
        return this.element;
    }

    @Override
    public int modifiedSlot() {
        return this.modifiedSlot;
    }

    @Override
    public void modifySlot(final int slot) {
        this.modifiedSlot = slot;
    }

    @NotNull
    @Override
    public ItemStack modifiedItem() {
        return this.modifiedItem;
    }

    @Override
    public void modifyItem(@NotNull final ItemStack newItem) {
        this.modifiedItem = newItem;
    }
}
