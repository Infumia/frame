package net.infumia.frame;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.infumia.frame.feature.FeatureInstaller;
import net.infumia.frame.logger.Logger;
import net.infumia.frame.pipeline.Pipelined;
import net.infumia.frame.pipeline.executor.PipelineExecutorFrame;
import net.infumia.frame.task.TaskFactory;
import net.infumia.frame.typedkey.TypedKeyStorageFactory;
import net.infumia.frame.typedkey.TypedKeyStorageImmutableBuilder;
import net.infumia.frame.view.ViewFactory;
import net.infumia.frame.view.ViewOpener;
import net.infumia.frame.view.creator.InventoryFactory;
import net.infumia.frame.viewer.ViewerFactory;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public interface Frame
    extends ViewOpener, FeatureInstaller<Frame>, Pipelined<PipelineExecutorFrame> {
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

    void register();

    void register(@NotNull Consumer<TypedKeyStorageImmutableBuilder> instanceConfigurer);

    void unregister();

    @NotNull
    Frame with(@NotNull Class<?> viewClass);

    @NotNull
    Logger logger();

    @NotNull
    TaskFactory taskFactory();

    @NotNull
    ViewFactory viewFactory();

    @NotNull
    ViewerFactory viewerFactory();

    @NotNull
    TypedKeyStorageFactory storageFactory();

    void storageFactory(@NotNull TypedKeyStorageFactory storageFactory);

    @NotNull
    InventoryFactory inventoryFactory();

    void inventoryFactory(@NotNull InventoryFactory inventoryFactory);

    @NotNull
    @ApiStatus.Internal
    <T> CompletableFuture<T> loggedFuture(
        @NotNull CompletableFuture<T> future,
        @NotNull String message,
        @NotNull final Object @NotNull... args
    );
}
