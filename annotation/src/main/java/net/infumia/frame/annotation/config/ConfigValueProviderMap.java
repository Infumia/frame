package net.infumia.frame.annotation.config;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import net.infumia.frame.api.util.Preconditions;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor
public final class ConfigValueProviderMap implements ConfigValueProvider {

    @NotNull
    private final Map<String, Object> config;

    @Nullable
    @Override
    public Object getRaw(@NotNull final String key) {
        return this.config.get(key);
    }

    @Nullable
    @Override
    public Integer getInt(@NotNull final String key) {
        final Object raw = this.getRaw(key);
        if (raw == null) {
            return null;
        }
        Preconditions.argument(raw instanceof Integer, "Raw value '%s' is not an integer!", raw);
        return (int) raw;
    }

    @Nullable
    @Override
    public String getString(@NotNull final String key) {
        final Object raw = this.getRaw(key);
        if (raw == null) {
            return null;
        }
        Preconditions.argument(raw instanceof String, "Raw value '%s' is not a String!", raw);
        return (String) raw;
    }

    @Nullable
    @Override
    public ItemStack getItem(@NotNull final String key) {
        final Object raw = this.getRaw(key);
        if (raw == null) {
            return null;
        }
        Preconditions.argument(
            raw instanceof ItemStack,
            "Raw value '%s' is not an ItemStack!",
            raw
        );
        return (ItemStack) raw;
    }
}
