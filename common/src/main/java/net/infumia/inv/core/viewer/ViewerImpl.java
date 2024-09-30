package net.infumia.inv.core.viewer;

import java.util.Objects;
import net.infumia.inv.api.metadata.MetadataAccess;
import net.infumia.inv.api.view.ViewContainer;
import net.infumia.inv.api.viewer.Viewer;
import net.infumia.inv.core.view.ViewRich;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ViewerImpl implements ViewerRich {

    private final Player player;
    private final ViewRich view;
    private final MetadataAccess metadata;

    public ViewerImpl(
        @NotNull final Player player,
        @NotNull final ViewRich view,
        @NotNull final MetadataAccess metadata
    ) {
        this.player = player;
        this.view = view;
        this.metadata = metadata;
    }

    public ViewerImpl(@NotNull final ViewerRich viewer) {
        this(viewer.player(), viewer.view(), viewer.metadata());
    }

    @NotNull
    @Override
    public ViewRich view() {
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
            this.player.openInventory(container.inventory());
        }
    }

    @NotNull
    @Override
    public MetadataAccess metadata() {
        return this.metadata;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.player);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ViewerImpl)) {
            return false;
        }
        return Objects.equals(this.player, ((Viewer) o).player());
    }
}
