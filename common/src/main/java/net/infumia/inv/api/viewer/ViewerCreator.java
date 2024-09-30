package net.infumia.inv.api.viewer;

import net.infumia.inv.api.view.View;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface ViewerCreator {
    @NotNull
    Viewer create(@NotNull Player player, @NotNull View view);
}
