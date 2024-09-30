package net.infumia.frame.pipeline.service.render;

import java.io.Closeable;
import net.infumia.frame.context.view.ContextRenderRich;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextRender;
import org.jetbrains.annotations.NotNull;

public final class ServiceStopUpdate
    implements PipelineServiceConsumer<PipelineContextRender.StopUpdate> {

    public static final PipelineServiceConsumer<PipelineContextRender.StopUpdate> INSTANCE =
        new ServiceStopUpdate();

    public static final String KEY = "stop-update";

    @Override
    public String key() {
        return ServiceStopUpdate.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextRender.StopUpdate ctx) {
        final Closeable updateTask = ((ContextRenderRich) ctx.context()).updateTask();
        if (updateTask != null) {
            try {
                updateTask.close();
            } catch (final Throwable e) {
                throw new RuntimeException("An error occurred while closing update task!", e);
            }
        }
    }

    private ServiceStopUpdate() {}
}
