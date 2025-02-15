package net.infumia.frame.config;

import org.bukkit.inventory.ItemStack;

public final class ConfigValueProviderEmpty implements ConfigValueProvider {

    public static final ConfigValueProvider INSTANCE = new ConfigValueProviderEmpty();

    private ConfigValueProviderEmpty() {}

    @Override
    public Object getRaw(final String key) {
        return null;
    }

    @Override
    public Integer getInt(final String key) {
        return null;
    }

    @Override
    public String getString(final String key) {
        return null;
    }

    @Override
    public ItemStack getItem(final String key) {
        return null;
    }
}
