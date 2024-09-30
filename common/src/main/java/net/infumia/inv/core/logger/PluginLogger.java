package net.infumia.inv.core.logger;

import java.util.logging.Level;
import net.infumia.inv.api.logger.Logger;
import net.infumia.inv.api.util.PaperLib;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public final class PluginLogger implements Logger {

    @NotNull
    private final Plugin plugin;

    private boolean debug = false;

    public PluginLogger(@NotNull final Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public synchronized boolean isDebugEnabled() {
        return this.debug;
    }

    @Override
    public synchronized void enableDebug(final boolean enable) {
        this.debug = enable;
    }

    @Override
    public void error(
        @NotNull final Throwable throwable,
        @NotNull final String message,
        @NotNull final Object @NotNull... args
    ) {
        if (PaperLib.isPaper()) {
            this.plugin.getSLF4JLogger().error(String.format(message, args), throwable);
        } else {
            this.plugin.getLogger().log(Level.SEVERE, String.format(message, args), throwable);
        }
    }

    @Override
    public void error(@NotNull final String message, @NotNull final Object @NotNull... args) {
        if (PaperLib.isPaper()) {
            this.plugin.getSLF4JLogger().error(String.format(message, args));
        } else {
            this.plugin.getLogger().severe(String.format(message, args));
        }
    }

    @Override
    public void warn(@NotNull final String message, @NotNull final Object @NotNull... args) {
        if (PaperLib.isPaper()) {
            this.plugin.getSLF4JLogger().warn(String.format(message, args));
        } else {
            this.plugin.getLogger().warning(String.format(message, args));
        }
    }

    @Override
    public void info(@NotNull final String message, @NotNull final Object @NotNull... args) {
        if (PaperLib.isPaper()) {
            this.plugin.getSLF4JLogger().info(String.format(message, args));
        } else {
            this.plugin.getLogger().info(String.format(message, args));
        }
    }

    @Override
    public void debug(@NotNull final String message, @NotNull final Object @NotNull... args) {
        if (this.isDebugEnabled()) {
            this.info(message, args);
        }
    }
}
