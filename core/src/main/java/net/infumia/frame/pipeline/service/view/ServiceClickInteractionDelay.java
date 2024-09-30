package net.infumia.frame.pipeline.service.view;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import net.infumia.frame.context.view.ContextClick;
import net.infumia.frame.metadata.MetadataAccess;
import net.infumia.frame.metadata.MetadataKeyHolder;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextView;
import net.infumia.frame.viewer.Viewer;
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
                .frame()
                .logger()
                .debug("Click event of viewer '%s' cancelled due to interaction delay.", clicker);
            ctx.cancelled(true);
            context.cancelled(true);
        }
    }

    private ServiceClickInteractionDelay() {}
}
