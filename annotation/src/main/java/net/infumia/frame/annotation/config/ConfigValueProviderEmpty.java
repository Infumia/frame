package net.infumia.frame.annotation.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConfigValueProviderEmpty implements ConfigValueProvider {

    public static final ConfigValueProvider INSTANCE = new ConfigValueProviderEmpty();

    @Nullable
    @Override
    public Object getRaw(@NotNull final String key) {
        return null;
    }

    @Nullable
    @Override
    public Integer getInt(@NotNull final String key) {
        return null;
    }

    @Nullable
    @Override
    public String getString(@NotNull final String key) {
        return null;
    }

    @Nullable
    @Override
    public ItemStack getItem(@NotNull final String key) {
        return null;
    }
}
