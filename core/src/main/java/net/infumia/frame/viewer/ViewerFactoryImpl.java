package net.infumia.frame.viewer;

import net.infumia.frame.metadata.MetadataAccessFactory;
import net.infumia.frame.view.View;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class ViewerFactoryImpl implements ViewerFactory {

    private final MetadataAccessFactory metadataAccessFactory;

    public ViewerFactoryImpl(@NotNull final MetadataAccessFactory metadataAccessFactory) {
        this.metadataAccessFactory = metadataAccessFactory;
    }

    @NotNull
    @Override
    public Viewer create(@NotNull final Player player, @NotNull final View view) {
        return new ViewerImpl(player, view, this.metadataAccessFactory.getOrCreate(player));
    }
}
