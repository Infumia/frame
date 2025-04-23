package net.infumia.frame.metadata;

import io.leangen.geantyref.TypeToken;
import java.util.Deque;
import java.util.Map;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.typedkey.TypedKey;
import net.infumia.frame.viewer.ContextualViewer;

public interface MetadataKeyHolder {
    TypedKey<ContextualViewer> CONTEXTUAL_VIEWER = TypedKey.of(
        ContextualViewer.class,
        "contextual-viewer"
    );

    TypedKey<ContextualViewer> TRANSITIONING_FROM = TypedKey.of(
        ContextualViewer.class,
        "transitioning-from"
    );

    TypedKey<Boolean> TRANSITIONING = TypedKey.of(
        Boolean.class,
        "transitioning"
    );

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
