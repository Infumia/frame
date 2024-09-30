package net.infumia.inv.api;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.infumia.inv.api.context.view.ContextRender;
import net.infumia.inv.api.logger.Logger;
import net.infumia.inv.api.pipeline.Pipelined;
import net.infumia.inv.api.pipeline.executor.PipelineExecutorManager;
import net.infumia.inv.api.task.TaskFactory;
import net.infumia.inv.api.typedkey.TypedKeyStorageFactory;
import net.infumia.inv.api.typedkey.TypedKeyStorageImmutableBuilder;
import net.infumia.inv.api.view.ViewCreator;
import net.infumia.inv.api.view.creator.InventoryCreator;
import net.infumia.inv.api.viewer.ViewerCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface InventoryManager extends Pipelined<PipelineExecutorManager> {
    @NotNull
    static InventoryManager create(@NotNull final Plugin plugin) {
        return Internal.factory().create(plugin);
    }

    @NotNull
    static InventoryManager create(
        @NotNull final Plugin plugin,
        final boolean unregisterOnDisable
    ) {
        return Internal.factory().create(plugin, unregisterOnDisable);
    }

    @NotNull
    static InventoryManager create(@NotNull final Plugin plugin, @NotNull final Logger logger) {
        return Internal.factory().create(plugin, logger);
    }

    @NotNull
    static InventoryManager create(
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

    void unregister();

    @NotNull
    InventoryManager with(@NotNull Class<?> viewClass);

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
