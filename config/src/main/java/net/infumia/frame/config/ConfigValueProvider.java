package net.infumia.frame.config;

import org.bukkit.inventory.ItemStack;

public interface ConfigValueProvider {
    Object getRaw(String key);

    Integer getInt(String key);

    String getString(String key);

    ItemStack getItem(String key);
}
