package net.infumia.inv.api.element;

import java.util.function.BiConsumer;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface ElementItemBuilderFactory {
    @NotNull
    ElementItemBuilder unsetSlot();

    @NotNull
    ElementItemBuilder layout(char layout);

    @NotNull
    ElementItemBuilder layout(char layout, @NotNull ItemStack item);

    void layout(char layout, @NotNull BiConsumer<Integer, ElementItemBuilder> configurer);

    @NotNull
    ElementItemBuilder slot(int slot);

    @NotNull
    ElementItemBuilder position(int row, int column);

    @NotNull
    ElementItemBuilder firstSlot();

    @NotNull
    ElementItemBuilder lastSlot();

    void availableSlot(@NotNull ItemStack item);

    void availableSlot(@NotNull BiConsumer<Integer, ElementItemBuilder> configurer);

    @NotNull
    ElementItemBuilder resultSlot();

    @NotNull
    ElementItemBuilder resultSlot(@NotNull ItemStack item);
}
