package net.infumia.frame;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.logger.Logger;
import net.infumia.frame.pipeline.Pipelined;
import net.infumia.frame.pipeline.executor.PipelineExecutorManager;
import net.infumia.frame.task.TaskFactory;
import net.infumia.frame.typedkey.TypedKeyStorageFactory;
import net.infumia.frame.typedkey.TypedKeyStorageImmutableBuilder;
import net.infumia.frame.view.ViewCreator;
import net.infumia.frame.view.creator.InventoryCreator;
import net.infumia.frame.viewer.ViewerCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Frame extends Pipelined<PipelineExecutorManager> {
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

    @NotNull
    CompletableFuture<@Nullable ContextRender> open(
        @NotNull Player player,
        @NotNull Class<?> viewClass
    );

    @NotNull
    CompletableFuture<@Nullable ContextRender> open(
        @NotNull Player player,
        @NotNull Class<?> viewClass,
        @NotNull Consumer<TypedKeyStorageImmutableBuilder> initialDataConfigurer
    );

    @NotNull
    CompletableFuture<@Nullable ContextRender> open(
        @NotNull Collection<Player> players,
        @NotNull Class<?> viewClass
    );

    @NotNull
    CompletableFuture<@Nullable ContextRender> open(
        @NotNull Collection<Player> players,
        @NotNull Class<?> viewClass,
        @NotNull Consumer<TypedKeyStorageImmutableBuilder> initialDataConfigurer
    );

    @NotNull
    CompletableFuture<ContextRender> openActive(
        @NotNull Player player,
        @NotNull ContextRender activeContext
    );

    @NotNull
    CompletableFuture<ContextRender> openActive(
        @NotNull Collection<Player> players,
        @NotNull ContextRender activeContext
    );
}
