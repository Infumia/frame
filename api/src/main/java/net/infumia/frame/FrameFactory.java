package net.infumia.frame;

import net.infumia.frame.logger.Logger;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface FrameFactory {
    @NotNull
    Frame create(@NotNull Plugin plugin, @NotNull Logger logger, boolean unregisterOnDisable);

    @NotNull
    Frame create(@NotNull Plugin plugin, @NotNull Logger logger);

    @NotNull
    Frame create(@NotNull Plugin plugin, boolean unregisterOnDisable);

    @NotNull
    Frame create(@NotNull Plugin plugin);
}
