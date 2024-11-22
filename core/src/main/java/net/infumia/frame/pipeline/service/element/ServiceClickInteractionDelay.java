package net.infumia.frame.pipeline.service.element;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import net.infumia.frame.context.element.ContextElementClick;
import net.infumia.frame.element.ElementRich;
import net.infumia.frame.metadata.MetadataAccess;
import net.infumia.frame.metadata.MetadataKeyHolder;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextElement;
import net.infumia.frame.viewer.Viewer;
import org.jetbrains.annotations.NotNull;

public final class ServiceClickInteractionDelay
    implements PipelineServiceConsumer<PipelineContextElement.Click> {

    public static final PipelineServiceConsumer<PipelineContextElement.Click> INSTANCE =
        new ServiceClickInteractionDelay();

    public static final String KEY = "interaction-delay";

    @NotNull
    @Override
    public String key() {
        return ServiceClickInteractionDelay.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextElement.Click ctx) {
        final ContextElementClick context = ctx.context();
        final ElementRich element = (ElementRich) context.element();
        final Duration delay = element.interactionDelay();
        if (delay == null || delay.isZero() || delay.isNegative()) {
            return;
        }
        final Viewer clicker = context.clicker();
        final MetadataAccess metadata = clicker.metadata();
        Map<String, Long> lastInteractions = metadata.get(
            MetadataKeyHolder.LAST_INTERACTION_ELEMENT
        );
        if (lastInteractions == null) {
            lastInteractions = new HashMap<>();
            metadata.setFixed(MetadataKeyHolder.LAST_INTERACTION_ELEMENT, lastInteractions);
        }
        final String key = element.key();
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
            final Consumer<ContextElementClick> onInteractionDelay = element.onInteractionDelay();
            if (onInteractionDelay != null) {
                onInteractionDelay.accept(context);
            }
        }
    }

    private ServiceClickInteractionDelay() {}
}
