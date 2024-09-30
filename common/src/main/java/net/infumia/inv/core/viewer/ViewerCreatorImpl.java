package net.infumia.inv.core.viewer;

import net.infumia.inv.api.metadata.MetadataAccessFactory;
import net.infumia.inv.api.view.View;
import net.infumia.inv.api.viewer.Viewer;
import net.infumia.inv.api.viewer.ViewerCreator;
import net.infumia.inv.core.view.ViewRich;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class ViewerCreatorImpl implements ViewerCreator {

    private final MetadataAccessFactory metadataAccessFactory;

    public ViewerCreatorImpl(@NotNull final MetadataAccessFactory metadataAccessFactory) {
        this.metadataAccessFactory = metadataAccessFactory;
    }

    @NotNull
    @Override
    public Viewer create(@NotNull final Player player, @NotNull final View view) {
        return new ViewerImpl(
            player,
            (ViewRich) view,
            this.metadataAccessFactory.getOrCreate(player)
        );
    }
}
