package net.infumia.inv.api.viewer;

import net.infumia.inv.api.metadata.MetadataAccess;
import net.infumia.inv.api.view.View;
import net.infumia.inv.api.view.ViewContainer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface Viewer {
    @NotNull
    View view();

    @NotNull
    Player player();

    void close();

    void open(@NotNull ViewContainer container);

    @NotNull
    MetadataAccess metadata();
}
