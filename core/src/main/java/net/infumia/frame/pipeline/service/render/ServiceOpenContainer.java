package net.infumia.frame.pipeline.service.render;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextRender;
import net.infumia.frame.viewer.Viewer;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public final class ServiceOpenContainer
    implements PipelineServiceConsumer<PipelineContextRender.OpenContainer> {

    public static final PipelineServiceConsumer<PipelineContextRender.OpenContainer> INSTANCE =
        new ServiceOpenContainer();

    public static final String KEY = "open-container";

    @NotNull
    @Override
    public String key() {
        return ServiceOpenContainer.KEY;
    }

    @NotNull
    @Override
    public CompletableFuture<State> handle(@NotNull final PipelineContextRender.OpenContainer ctx) {
        if (Bukkit.isPrimaryThread()) {
            this.openUnsafe(ctx);
            return CompletableFuture.completedFuture(State.CONTINUE);
        } else {
            return ctx
                .context()
                .frame()
                .taskFactory()
                .syncFuture(() -> this.openUnsafe(ctx))
                .thenApply(__ -> State.CONTINUE);
        }
    }

    private ServiceOpenContainer() {}

    private void openUnsafe(@NotNull final PipelineContextRender.OpenContainer ctx) {
        for (final Viewer viewer : ctx.viewers()) {
            ctx.context().container().open(viewer);
        }
    }
}
