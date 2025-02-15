package net.infumia.frame.pipeline.service.render;

import java.time.Duration;
import net.infumia.frame.Frame;
import net.infumia.frame.Preconditions;
import net.infumia.frame.context.view.ContextRenderRich;
import net.infumia.frame.extension.CompletableFutureExtensions;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextRender;
import org.jetbrains.annotations.NotNull;

public final class ServiceStartUpdate
    implements PipelineServiceConsumer<PipelineContextRender.StartUpdate> {

    public static final PipelineServiceConsumer<PipelineContextRender.StartUpdate> INSTANCE =
        new ServiceStartUpdate();

    public static final String KEY = "start-update";

    @NotNull
    @Override
    public String key() {
        return ServiceStartUpdate.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextRender.StartUpdate ctx) {
        final ContextRenderRich context = (ContextRenderRich) ctx.context();
        final Duration updateInterval = Preconditions.stateNotNull(
            context.config().updateInterval(),
            "Update interval cannot be null in start-update service!"
        );

        final Frame frame = context.frame();
        context.updateTask(
            frame
                .taskFactory()
                .sync(
                    () ->
                        CompletableFutureExtensions.logError(
                            context.pipelines().executeUpdate(),
                            frame.logger(),
                            "An error occurred while running the update task of view '%s'.",
                            context.view().instance()
                        ),
                    updateInterval,
                    updateInterval
                )
        );
    }

    private ServiceStartUpdate() {}
}
