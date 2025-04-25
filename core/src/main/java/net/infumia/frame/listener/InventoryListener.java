package net.infumia.frame.listener;

import java.util.function.Consumer;
import net.infumia.frame.extension.CompletableFutureExtensions;
import net.infumia.frame.logger.Logger;
import net.infumia.frame.metadata.MetadataAccess;
import net.infumia.frame.metadata.MetadataAccessFactory;
import net.infumia.frame.metadata.MetadataKeyHolder;
import net.infumia.frame.view.InventoryHolderView;
import net.infumia.frame.view.ViewEventHandler;
import net.infumia.frame.viewer.ContextualViewer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public final class InventoryListener implements Listener {

    private final Plugin plugin;
    private final Logger logger;
    private final MetadataAccessFactory metadataAccessFactory;
    private final Runnable onUnregister;

    public InventoryListener(
        @NotNull final Plugin plugin,
        @NotNull final Logger logger,
        @NotNull final MetadataAccessFactory metadataAccessFactory,
        @NotNull final Runnable onUnregister
    ) {
        this.plugin = plugin;
        this.logger = logger;
        this.metadataAccessFactory = metadataAccessFactory;
        this.onUnregister = onUnregister;
    }

    public void register() {
        Bukkit.getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onPluginDisable(final PluginDisableEvent event) {
        if (event.getPlugin().getName().equals(this.plugin.getName())) {
            this.onUnregister.run();
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClose(final InventoryCloseEvent event) {
        final InventoryHolder inventoryHolder = event.getInventory().getHolder();
        if (!(inventoryHolder instanceof InventoryHolderView)) {
            return;
        }
        this.ifTransitioning(event.getPlayer(), viewer ->
                CompletableFutureExtensions.logError(
                    ((ViewEventHandler) viewer.view()).simulateClose(viewer),
                    this.logger,
                    "Error occurred while viewer '%s' closes an inventory",
                    viewer
                )
            );
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(final InventoryClickEvent event) {
        final InventoryHolder inventoryHolder = event.getInventory().getHolder();
        if (!(inventoryHolder instanceof InventoryHolderView)) {
            return;
        }
        this.ifContextualViewer(event.getWhoClicked(), viewer ->
                CompletableFutureExtensions.logError(
                    ((ViewEventHandler) viewer.view()).simulateClick(viewer, event),
                    this.logger,
                    "Error occurred while viewer '%s' clicks an inventory!",
                    viewer
                )
            );
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryDrag(final InventoryDragEvent event) {
        final InventoryHolder inventoryHolder = event.getInventory().getHolder();
        if (!(inventoryHolder instanceof InventoryHolderView)) {
            return;
        }
        this.ifContextualViewer(event.getWhoClicked(), viewer ->
                ((ViewEventHandler) viewer.view()).handleInventoryDrag(viewer, event)
            );
    }

    @EventHandler(ignoreCancelled = true)
    public void onItemPickup(final PlayerPickupItemEvent event) {
        this.ifContextualViewer(event.getPlayer(), viewer ->
                ((ViewEventHandler) viewer.view()).handleItemPickup(viewer, event)
            );
    }

    @EventHandler(ignoreCancelled = true)
    public void onItemDrop(final PlayerDropItemEvent event) {
        this.ifContextualViewer(event.getPlayer(), viewer ->
                ((ViewEventHandler) viewer.view()).handleItemDrop(viewer, event)
            );
    }

    private void ifContextualViewer(
        @NotNull final Metadatable metadatable,
        @NotNull final Consumer<ContextualViewer> consumer
    ) {
        final ContextualViewer viewer =
            this.metadataAccessFactory.getOrCreate(metadatable).get(
                    MetadataKeyHolder.CONTEXTUAL_VIEWER
                );
        if (viewer != null) {
            consumer.accept(viewer);
        }
    }

    private void ifTransitioning(
        @NotNull final Metadatable metadatable,
        @NotNull final Consumer<ContextualViewer> consumer
    ) {
        final MetadataAccess access = this.metadataAccessFactory.getOrCreate(metadatable);
        final ContextualViewer transitioningFrom = access.get(MetadataKeyHolder.TRANSITIONING_FROM);
        if (transitioningFrom == null) {
            this.ifContextualViewer(metadatable, consumer);
        } else {
            consumer.accept(transitioningFrom);
        }
    }
}
