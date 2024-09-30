package net.infumia.frame;

import net.infumia.frame.logger.Logger;
import net.infumia.frame.logger.PluginLogger;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public final class FrameFactoryImpl implements FrameFactory {

    public static final FrameFactory INSTANCE = new FrameFactoryImpl();

    private FrameFactoryImpl() {}

    @NotNull
    @Override
    public Frame create(
        @NotNull final Plugin plugin,
        @NotNull final Logger logger,
        final boolean unregisterOnDisable
    ) {
        return new FrameImpl(plugin, logger, unregisterOnDisable);
    }

    @NotNull
    @Override
    public Frame create(@NotNull final Plugin plugin, @NotNull final Logger logger) {
        return this.create(plugin, logger, true);
    }

    @NotNull
    @Override
    public Frame create(@NotNull final Plugin plugin, final boolean unregisterOnDisable) {
        return this.create(plugin, new PluginLogger(plugin), unregisterOnDisable);
    }

    @NotNull
    @Override
    public Frame create(@NotNull final Plugin plugin) {
        return this.create(plugin, true);
    }
}
