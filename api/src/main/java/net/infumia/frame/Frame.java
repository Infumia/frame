package net.infumia.frame;

import java.util.function.Consumer;
import net.infumia.frame.feature.FeatureBuilderFactory;
import net.infumia.frame.logger.Logger;
import net.infumia.frame.pipeline.Pipelined;
import net.infumia.frame.pipeline.executor.PipelineExecutorFrame;
import net.infumia.frame.task.TaskFactory;
import net.infumia.frame.typedkey.TypedKeyStorageFactory;
import net.infumia.frame.typedkey.TypedKeyStorageImmutableBuilder;
import net.infumia.frame.view.ViewCreator;
import net.infumia.frame.view.ViewOpener;
import net.infumia.frame.view.creator.InventoryCreator;
import net.infumia.frame.viewer.ViewerCreator;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface Frame
    extends ViewOpener, FeatureBuilderFactory<Frame>, Pipelined<PipelineExecutorFrame> {
    @NotNull
    static Frame create(@NotNull final Plugin plugin) {
        return Internal.factory().create(plugin);
    }

    @NotNull
    static Frame create(@NotNull final Plugin plugin, final boolean unregisterOnDisable) {
        return Internal.factory().create(plugin, unregisterOnDisable);
    }

    @NotNull
    static Frame create(@NotNull final Plugin plugin, @NotNull final Logger logger) {
        return Internal.factory().create(plugin, logger);
    }

    @NotNull
    static Frame create(
        @NotNull final Plugin plugin,
        @NotNull final Logger logger,
        final boolean unregisterOnDisable
    ) {
        return Internal.factory().create(plugin, logger, unregisterOnDisable);
    }

    @NotNull
    Logger logger();

    @NotNull
    TaskFactory taskFactory();

    @NotNull
    ViewCreator viewCreator();

    @NotNull
    ViewerCreator viewerCreator();

    @NotNull
    TypedKeyStorageFactory storageFactory();

    void storageFactory(@NotNull TypedKeyStorageFactory storageFactory);

    @NotNull
    InventoryCreator inventoryCreator();

    void inventoryCreator(@NotNull InventoryCreator inventoryCreator);

    void register();

    void register(@NotNull Consumer<TypedKeyStorageImmutableBuilder> instanceConfigurer);

    void unregister();

    @NotNull
    Frame with(@NotNull Class<?> viewClass);
}
