package net.infumia.frame.viewer;

import net.infumia.frame.metadata.MetadataAccess;
import net.infumia.frame.view.View;
import net.infumia.frame.view.ViewContainer;
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
