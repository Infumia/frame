package net.infumia.frame.view;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.service.ConsumerService;
import net.infumia.frame.typedkey.TypedKeyStorageImmutable;
import net.infumia.frame.viewer.ContextualViewer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ViewEventHandler {
    @NotNull
    CompletableFuture<ConsumerService.State> simulateOnInit();

    @NotNull
    CompletableFuture<@Nullable ContextRender> simulateOpen(
        @NotNull Collection<Player> viewers,
        @NotNull TypedKeyStorageImmutable initialData
    );

    @NotNull
    CompletableFuture<ContextRender> simulateOpenActive(
        @NotNull ContextRender activeContext,
        @NotNull Collection<Player> viewers
    );

    @NotNull
    CompletableFuture<ConsumerService.State> simulateClick(
        @NotNull ContextualViewer viewer,
        @NotNull InventoryClickEvent event
    );

    @NotNull
    CompletableFuture<ConsumerService.State> simulateClose(@NotNull ContextualViewer viewer);

    void handleItemPickup(@NotNull ContextualViewer viewer, @NotNull PlayerPickupItemEvent event);

    void handleItemDrop(@NotNull ContextualViewer viewer, @NotNull PlayerDropItemEvent event);

    void handleInventoryDrag(@NotNull ContextualViewer viewer, @NotNull InventoryDragEvent event);
}
