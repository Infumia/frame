package net.infumia.inv.core.pipeline.service.render;

import java.time.Duration;
import net.infumia.inv.api.InventoryManager;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextRender;
import net.infumia.inv.api.util.Preconditions;
import net.infumia.inv.core.context.view.ContextRenderRich;
import net.infumia.inv.core.extension.CompletableFutureExtensions;
import org.jetbrains.annotations.NotNull;

public final class ServiceStartUpdate
    implements PipelineServiceConsumer<PipelineContextRender.StartUpdate> {

    public static final PipelineServiceConsumer<PipelineContextRender.StartUpdate> INSTANCE =
        new ServiceStartUpdate();

    public static final String KEY = "start-update";

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

        final InventoryManager manager = context.manager();
        context.updateTask(
            manager
                .taskFactory()
                .sync(
                    () ->
                        CompletableFutureExtensions.logError(
                            context.pipelines().executeUpdate(),
                            manager.logger(),
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
