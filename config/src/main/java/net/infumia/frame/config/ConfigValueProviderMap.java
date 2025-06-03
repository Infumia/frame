package net.infumia.frame.config;

import java.util.Map;
import net.infumia.frame.Preconditions;
import org.bukkit.inventory.ItemStack;

public final class ConfigValueProviderMap implements ConfigValueProvider {

    private final Map<String, Object> config;

    public ConfigValueProviderMap(final Map<String, Object> config) {
        this.config = Preconditions.argumentNotNull(config, "config");
    }

    @Override
    public Object getRaw(final String key) {
        Preconditions.argumentNotNull(key, "key");

        return this.config.get(key);
    }

    @Override
    public Integer getInt(final String key) {
        Preconditions.argumentNotNull(key, "key");

        final Object raw = this.getRaw(key);
        if (raw == null) {
            return null;
        }
        Preconditions.argument(raw instanceof Integer, "Raw value '%s' is not an integer!", raw);
        return (int) raw;
    }

    @Override
    public String getString(final String key) {
        Preconditions.argumentNotNull(key, "key");

        final Object raw = this.getRaw(key);
        if (raw == null) {
            return null;
        }
        Preconditions.argument(raw instanceof String, "Raw value '%s' is not a String!", raw);
        return (String) raw;
    }

    @Override
    public ItemStack getItem(final String key) {
        Preconditions.argumentNotNull(key, "key");

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
