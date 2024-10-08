package net.infumia.frame.pipeline.service.render;

import java.io.Closeable;
import net.infumia.frame.context.view.ContextRenderRich;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextRender;
import org.jetbrains.annotations.NotNull;

public final class ServiceStartUpdateInvalidate
    implements PipelineServiceConsumer<PipelineContextRender.StartUpdate> {

    public static final PipelineServiceConsumer<PipelineContextRender.StartUpdate> INSTANCE =
        new ServiceStartUpdateInvalidate();

    public static final String KEY = "invalidate";

    @NotNull
    @Override
    public String key() {
        return ServiceStartUpdateInvalidate.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextRender.StartUpdate ctx) {
        final ContextRenderRich context = (ContextRenderRich) ctx.context();

        final Closeable oldTask = context.updateTask();
        if (oldTask == null) {
            return;
        }

        try {
            oldTask.close();
            context.updateTask(null);
        } catch (final Throwable e) {
            context
                .frame()
                .logger()
                .error(
                    e,
                    "An error occurred while closing the old update task of view '%s'.",
                    context.view().instance()
                );
        }
    }

    private ServiceStartUpdateInvalidate() {}
}
