package net.infumia.frame.element;

import java.util.function.Consumer;
import java.util.function.Function;
import net.infumia.frame.context.element.ContextElementItemClick;
import net.infumia.frame.context.element.ContextElementItemRender;
import net.infumia.frame.context.element.ContextElementItemUpdate;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface ElementItemBuilder extends ElementBuilder {
    @NotNull
    ElementItemBuilder item(@NotNull ItemStack item);

    @NotNull
    ElementItemBuilder slot(int slot);

    @NotNull
    ElementItemBuilder onClick(@NotNull Consumer<ContextElementItemClick> onClick);

    @NotNull
    ElementItemBuilder onClick(@NotNull Runnable onClick);

    @NotNull
    ElementItemBuilder onRender(@NotNull Consumer<ContextElementItemRender> onRender);

    @NotNull
    ElementItemBuilder renderWith(
        @NotNull Function<ContextElementItemRender, ItemStack> renderWith
    );

    @NotNull
    ElementItemBuilder onUpdate(@NotNull Consumer<ContextElementItemUpdate> onUpdate);
}
