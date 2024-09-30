package net.infumia.inv.core.pipeline.service.view;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import net.infumia.inv.api.context.view.ContextClick;
import net.infumia.inv.api.metadata.MetadataAccess;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextView;
import net.infumia.inv.api.viewer.Viewer;
import net.infumia.inv.core.metadata.MetadataKeyHolder;
import org.jetbrains.annotations.NotNull;

public final class ServiceClickInteractionDelay
    implements PipelineServiceConsumer<PipelineContextView.Click> {

    public static final PipelineServiceConsumer<PipelineContextView.Click> INSTANCE =
        new ServiceClickInteractionDelay();

    public static final String KEY = "interaction-delay";

    @Override
    public String key() {
        return ServiceClickInteractionDelay.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextView.Click ctx) {
        final ContextClick context = ctx.context();
        final Duration delay = context.config().interactionDelay();
        if (delay == null || delay.isZero() || delay.isNegative()) {
            return;
        }
        final Viewer clicker = context.clicker();
        final MetadataAccess metadata = clicker.metadata();
        Map<String, Long> lastInteractions = metadata.get(MetadataKeyHolder.LAST_INTERACTION);
        if (lastInteractions == null) {
            lastInteractions = new HashMap<>();
            metadata.setFixed(MetadataKeyHolder.LAST_INTERACTION, lastInteractions);
        }
        final String key = context.id().toString();
        final Long lastInteraction = lastInteractions.get(key);
        final long now = System.currentTimeMillis();
        if (lastInteraction == null) {
            lastInteractions.put(key, now);
            return;
        }
        final long elapsed = now - lastInteraction;
        if (elapsed > delay.toMillis()) {
            lastInteractions.put(key, now);
        } else {
            context
                .manager()
                .logger()
                .debug("Click event of viewer '%s' cancelled due to interaction delay.", clicker);
            ctx.cancelled(true);
            context.cancelled(true);
        }
    }

    private ServiceClickInteractionDelay() {}
}
