package net.infumia.examples;

import net.infumia.frame.Frame;
import net.infumia.frame.typedkey.TypedKey;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public final class ExamplePlugin extends JavaPlugin implements CommandExecutor {
    public static final TypedKey<CommandSender> CONSOLE_KEY =
        TypedKey.of(CommandSender.class, "console");

    private final Frame frame = Frame.create(this)
        .with(ViewExample.class);

    @Override
    public void onEnable() {
        this.frame.register(builder ->
            builder.add(ExamplePlugin.CONSOLE_KEY, Bukkit.getConsoleSender()));
        this.getCommand("test").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command, @NotNull final String label, final String @NotNull [] args) {
        CompletableFuture.runAsync(() ->
            this.frame.open(((Player) sender), ViewExample.class));
        return true;
    }
}
