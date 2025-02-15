package net.infumia.frame.config;

import java.util.Map;
import java.util.Objects;
import net.infumia.frame.Preconditions;
import org.bukkit.inventory.ItemStack;

public final class ConfigValueProviderMap implements ConfigValueProvider {

    private final Map<String, Object> config;

    public ConfigValueProviderMap(final Map<String, Object> config) {
        this.config = Objects.requireNonNull(config, "config");
    }

    @Override
    public Object getRaw(final String key) {
        Objects.requireNonNull(key, "key");

        return this.config.get(key);
    }

    @Override
    public Integer getInt(final String key) {
        Objects.requireNonNull(key, "key");

        final Object raw = this.getRaw(key);
        if (raw == null) {
            return null;
        }
        Preconditions.argument(raw instanceof Integer, "Raw value '%s' is not an integer!", raw);
        return (int) raw;
    }

    @Override
    public String getString(final String key) {
        Objects.requireNonNull(key, "key");

        final Object raw = this.getRaw(key);
        if (raw == null) {
            return null;
        }
        Preconditions.argument(raw instanceof String, "Raw value '%s' is not a String!", raw);
        return (String) raw;
    }

    @Override
    public ItemStack getItem(final String key) {
        Objects.requireNonNull(key, "key");

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
