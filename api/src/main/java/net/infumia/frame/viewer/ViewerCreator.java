package net.infumia.frame.viewer;

import net.infumia.frame.view.View;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface ViewerCreator {
    @NotNull
    Viewer create(@NotNull Player player, @NotNull View view);
}
