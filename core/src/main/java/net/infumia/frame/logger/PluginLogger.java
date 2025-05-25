package net.infumia.frame.logger;

import java.util.logging.Level;
import net.infumia.frame.util.PaperLib;
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
        final String formatted = String.format(message, args);
        if (PaperLib.isPaper()) {
            this.plugin.getSLF4JLogger().error(formatted, throwable);
        } else {
            this.plugin.getLogger().log(Level.SEVERE, formatted, throwable);
        }
    }

    @Override
    public void error(@NotNull final String message, @NotNull final Object @NotNull... args) {
        final String formatted = String.format(message, args);
        if (PaperLib.isPaper()) {
            this.plugin.getSLF4JLogger().error(formatted);
        } else {
            this.plugin.getLogger().severe(formatted);
        }
    }

    @Override
    public void warn(@NotNull final String message, @NotNull final Object @NotNull... args) {
        final String formatted = String.format(message, args);
        if (PaperLib.isPaper()) {
            this.plugin.getSLF4JLogger().warn(formatted);
        } else {
            this.plugin.getLogger().warning(formatted);
        }
    }

    @Override
    public void info(@NotNull final String message, @NotNull final Object @NotNull... args) {
        final String formatted = String.format(message, args);
        if (PaperLib.isPaper()) {
            this.plugin.getSLF4JLogger().info(formatted);
        } else {
            this.plugin.getLogger().info(formatted);
        }
    }

    @Override
    public void debug(@NotNull final String message, @NotNull final Object @NotNull... args) {
        if (this.isDebugEnabled()) {
            this.info(message, args);
        }
    }
}
