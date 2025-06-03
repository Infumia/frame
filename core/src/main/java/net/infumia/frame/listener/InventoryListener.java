package net.infumia.frame.listener;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.infumia.frame.Frame;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.metadata.MetadataAccessFactory;
import net.infumia.frame.metadata.MetadataKeyHolder;
import net.infumia.frame.view.InventoryHolderFrame;
import net.infumia.frame.view.ViewEventHandler;
import net.infumia.frame.viewer.ContextualViewer;
import net.infumia.frame.viewer.Viewer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public final class InventoryListener implements Listener {

    private final Plugin plugin;
    private final MetadataAccessFactory metadataAccessFactory;
    private final boolean unregisterOnDisable;
    private final Frame frame;

    public InventoryListener(
        @NotNull final Frame frame,
        @NotNull final Plugin plugin,
        @NotNull final MetadataAccessFactory metadataAccessFactory,
        final boolean unregisterOnDisable
    ) {
        this.frame = frame;
        this.plugin = plugin;
        this.metadataAccessFactory = metadataAccessFactory;
        this.unregisterOnDisable = unregisterOnDisable;
    }

    public void register() {
        Bukkit.getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onPluginDisable(final PluginDisableEvent event) {
        if (this.unregisterOnDisable && event.getPlugin().getName().equals(this.plugin.getName())) {
            this.frame.unregister();
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClose(final InventoryCloseEvent event) {
        this.extractContext(event.getInventory(), event.getPlayer(), (viewer, ctx) ->
                this.frame.loggedFuture(
                        ((ViewEventHandler) viewer.view()).simulateClose(ctx, viewer),
                        "Error occurred while viewer '%s' closes an inventory",
                        viewer
                    )
            );
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(final InventoryClickEvent event) {
        this.extractContext(event.getInventory(), event.getWhoClicked(), (viewer, ctx) ->
                this.frame.loggedFuture(
                        ((ViewEventHandler) viewer.view()).simulateClick(ctx, viewer, event),
                        "Error occurred while viewer '%s' clicks an inventory!",
                        viewer
                    )
            );
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryDrag(final InventoryDragEvent event) {
        this.extractContext(event.getInventory(), event.getWhoClicked(), (viewer, ctx) ->
                ((ViewEventHandler) viewer.view()).handleInventoryDrag(ctx, event)
            );
    }

    @EventHandler(ignoreCancelled = true)
    public void onItemPickup(final PlayerPickupItemEvent event) {
        this.ifContextualViewer(event.getPlayer(), viewer ->
                ((ViewEventHandler) viewer.view()).handleItemPickup(viewer.context(), event)
            );
    }

    @EventHandler(ignoreCancelled = true)
    public void onItemDrop(final PlayerDropItemEvent event) {
        this.ifContextualViewer(event.getPlayer(), viewer ->
                ((ViewEventHandler) viewer.view()).handleItemDrop(viewer.context(), event)
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

    private void extractContext(
        @NotNull final Inventory inventory,
        @NotNull final Entity involved,
        @NotNull final BiConsumer<Viewer, ContextRender> function
    ) {
        final InventoryHolder inventoryHolder = inventory.getHolder();
        if (!(inventoryHolder instanceof InventoryHolderFrame)) {
            return;
        }
        final ContextRender ctx = ((InventoryHolderFrame) inventoryHolder).context();
        ctx
            .viewers()
            .stream()
            .filter(viewer -> viewer.player().getUniqueId().equals(involved.getUniqueId()))
            .findFirst()
            .ifPresent(viewer -> function.accept(viewer, ctx));
    }
}
