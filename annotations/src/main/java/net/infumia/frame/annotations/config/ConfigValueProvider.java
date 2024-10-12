package net.infumia.frame.annotations.config;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ConfigValueProvider {
    @Nullable
    Object getRaw(@NotNull String key);

    @Nullable
    Integer getInt(@NotNull String key);

    @Nullable
    String getString(@NotNull String key);

    @Nullable
    ItemStack getItem(@NotNull String key);
}
