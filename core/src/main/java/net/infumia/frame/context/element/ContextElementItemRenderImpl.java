package net.infumia.frame.context.element;

import net.infumia.frame.element.item.ElementItem;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class ContextElementItemRenderImpl
    extends ContextElementRenderImpl
    implements ContextElementItemRender {

    private final ElementItem element;
    private int modifiedSlot;
    private ItemStack modifiedItem;

    public ContextElementItemRenderImpl(
        @NotNull final ContextElementRender context,
        @NotNull final ElementItem element
    ) {
        super(context);
        this.element = element;
        this.modifiedSlot = element.slot();
        this.modifiedItem = element.item();
    }

    @NotNull
    @Override
    public ElementItem element() {
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
