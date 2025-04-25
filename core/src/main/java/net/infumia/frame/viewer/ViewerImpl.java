package net.infumia.frame.viewer;

import java.util.Objects;
import net.infumia.frame.metadata.MetadataAccess;
import net.infumia.frame.view.View;
import net.infumia.frame.view.ViewContainer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ViewerImpl implements Viewer {

    private final Player player;
    private final View view;
    private final MetadataAccess metadata;

    public ViewerImpl(
        @NotNull final Player player,
        @NotNull final View view,
        @NotNull final MetadataAccess metadata
    ) {
        this.player = player;
        this.view = view;
        this.metadata = metadata;
    }

    public ViewerImpl(@NotNull final Viewer viewer) {
        this(viewer.player(), viewer.view(), viewer.metadata());
    }

    @NotNull
    @Override
    public View view() {
        return this.view;
    }

    @NotNull
    @Override
    public Player player() {
        return this.player;
    }

    @Override
    public void close() {
        if (this.player.isOnline()) {
            this.player.closeInventory();
        }
    }

    @Override
    public void open(@NotNull final ViewContainer container) {
        if (this.player.isOnline()) {
            this.player.openInventory(container.inventoryHolder().getInventory());
        }
    }

    @NotNull
    @Override
    public MetadataAccess metadata() {
        return this.metadata;
    }

    @Override
    public int hashCode() {
        return this.player.hashCode();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Player) {
            return this.player.equals(o);
        } else {
            return Objects.equals(this.player, ((Viewer) o).player());
        }
    }
}
