package net.infumia.frame.metadata;

import io.leangen.geantyref.TypeToken;
import java.util.Deque;
import java.util.Map;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.typedkey.TypedKey;
import net.infumia.frame.view.ViewEventHandler;
import net.infumia.frame.viewer.ContextualViewer;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public interface MetadataKeyHolder {
    TypedKey<ContextualViewer> CONTEXTUAL_VIEWER = TypedKey.of(
        ContextualViewer.class,
        "contextual-viewer"
    );

    /**
     * The key for players transition from a frame to another frame inventory.
     */
    TypedKey<ContextualViewer> TRANSITIONING_FROM = TypedKey.of(
        ContextualViewer.class,
        "transitioning-from"
    );

    /**
     * The key for players transition from any inventory to frame inventory.
     * <p>
     * This helps to recognize when a player tries to open a frame inventory while views a non-frame inventory.
     * <p>
     * The reason why this is important is because,
     * Bukkit does not send {@link InventoryCloseEvent} before {@link InventoryOpenEvent} so,
     * it creates bugs where while a player views a regular chest inventory then tries to open a frame inventory and,
     * since {@link #TRANSITIONING_FROM} not exists in this stage,
     * {@link #CONTEXTUAL_VIEWER} is get being used in InventoryListener#transitioningOrCurrent
     * which is the newly opened frame inventory then frame thinks the frame inventory is closed however,
     * for the player it's still opened and player can interact with everything
     * without triggering the {@link InventoryClickEvent} for {@link ViewEventHandler}.
     */
    TypedKey<Boolean> TRANSITIONING = TypedKey.of(Boolean.class, "transitioning");

    TypedKey<Boolean> FORCED_CLOSE = TypedKey.of(boolean.class, "forced-close");

    TypedKey<Deque<ContextRender>> PREVIOUS_VIEWS = TypedKey.of(
        new TypeToken<Deque<ContextRender>>() {},
        "previous-views"
    );

    TypedKey<Map<String, Long>> LAST_INTERACTION = TypedKey.of(
        new TypeToken<Map<String, Long>>() {},
        "last-interaction"
    );

    TypedKey<Map<String, Long>> LAST_INTERACTION_ELEMENT = TypedKey.of(
        new TypeToken<Map<String, Long>>() {},
        "last-interaction-element"
    );
}
