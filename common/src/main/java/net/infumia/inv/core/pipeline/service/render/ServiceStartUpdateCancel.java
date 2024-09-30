package net.infumia.inv.core.pipeline.service.render;

import java.time.Duration;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextRender;
import net.infumia.inv.core.context.view.ContextRenderRich;
import org.jetbrains.annotations.NotNull;

public final class ServiceStartUpdateCancel
    implements PipelineServiceConsumer<PipelineContextRender.StartUpdate> {

    public static final PipelineServiceConsumer<PipelineContextRender.StartUpdate> INSTANCE =
        new ServiceStartUpdateCancel();

    public static final String KEY = "cancel";

    @Override
    public String key() {
        return ServiceStartUpdateCancel.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextRender.StartUpdate ctx) {
        final ContextRenderRich context = (ContextRenderRich) ctx.context();
        final Duration updateInterval = context.config().updateInterval();

        if (
            updateInterval == null ||
            updateInterval.isNegative() ||
            updateInterval.isZero() ||
            context.viewers().isEmpty()
        ) {
            ctx.cancelled(true);
        }
    }

    private ServiceStartUpdateCancel() {}
}
