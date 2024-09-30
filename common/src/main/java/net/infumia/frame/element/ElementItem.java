package net.infumia.frame.element;

import java.util.function.Consumer;
import net.infumia.frame.context.element.ContextElementItemClick;
import net.infumia.frame.context.element.ContextElementItemRender;
import net.infumia.frame.context.element.ContextElementItemUpdate;
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
