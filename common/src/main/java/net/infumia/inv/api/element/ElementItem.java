package net.infumia.inv.api.element;

import java.util.function.Consumer;
import net.infumia.inv.api.context.element.ContextElementItemClick;
import net.infumia.inv.api.context.element.ContextElementItemRender;
import net.infumia.inv.api.context.element.ContextElementItemUpdate;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ElementItem extends Element {
    @NotNull
    ItemStack item();

    int slot();

    @Nullable
    Consumer<ContextElementItemClick> onClick();

    @Nullable
    Consumer<ContextElementItemRender> onRender();

    @Nullable
    Consumer<ContextElementItemUpdate> onUpdate();
}
