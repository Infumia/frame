package net.infumia.inv.core.metadata;

import io.leangen.geantyref.TypeToken;
import java.util.Deque;
import java.util.Map;
import net.infumia.inv.api.typedkey.TypedKey;
import net.infumia.inv.core.context.view.ContextRenderRich;
import net.infumia.inv.core.viewer.ContextualViewerRich;

public interface MetadataKeyHolder {
    TypedKey<ContextualViewerRich> CONTEXTUAL_VIEWER = TypedKey.of(
        ContextualViewerRich.class,
        "contextual-viewer"
    );

    TypedKey<ContextualViewerRich> TRANSITIONING_FROM = TypedKey.of(
        ContextualViewerRich.class,
        "transitioning-from"
    );

    TypedKey<Boolean> FORCED_CLOSE = TypedKey.of(boolean.class, "forced-close");

    TypedKey<Deque<ContextRenderRich>> PREVIOUS_VIEWS = TypedKey.of(
        new TypeToken<Deque<ContextRenderRich>>() {},
        "previous-views"
    );

    TypedKey<Map<String, Long>> LAST_INTERACTION = TypedKey.of(
        new TypeToken<Map<String, Long>>() {},
        "last-interaction"
    );
}
