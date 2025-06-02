package net.infumia.examples;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.Frame;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class ExamplePlugin extends JavaPlugin implements CommandExecutor {

    private final Frame frame = Frame.create(this).with(ViewExample.class);

    @Override
    public void onEnable() {
        this.frame.register();
        this.getCommand("test").setExecutor(this);
    }

    @Override
    public boolean onCommand(
        @NotNull final CommandSender sender,
        @NotNull final Command command,
        @NotNull final String label,
        final String@NotNull[] args
    ) {
        CompletableFuture.runAsync(() -> this.frame.open(((Player) sender), ViewExample.class));
        return true;
    }
}
