package net.infumia.inv.core.view;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.infumia.inv.api.context.view.ContextRender;
import net.infumia.inv.api.service.ConsumerService;
import net.infumia.inv.api.typedkey.TypedKeyStorageImmutable;
import net.infumia.inv.core.context.view.ContextRenderRich;
import net.infumia.inv.core.viewer.ContextualViewerRich;
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
        @NotNull ContextRenderRich activeContext,
        @NotNull Collection<Player> viewers
    );

    @NotNull
    CompletableFuture<ConsumerService.State> simulateClick(
        @NotNull ContextualViewerRich viewer,
        @NotNull InventoryClickEvent event
    );

    @NotNull
    CompletableFuture<ConsumerService.State> simulateClose(@NotNull ContextualViewerRich viewer);

    void handleItemPickup(
        @NotNull ContextualViewerRich viewer,
        @NotNull PlayerPickupItemEvent event
    );

    void handleItemDrop(@NotNull ContextualViewerRich viewer, @NotNull PlayerDropItemEvent event);

    void handleInventoryDrag(
        @NotNull ContextualViewerRich viewer,
        @NotNull InventoryDragEvent event
    );
}
