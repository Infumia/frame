package net.infumia.frame.pipeline.service.view;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.context.view.ContextRenderImpl;
import net.infumia.frame.pipeline.PipelineService;
import net.infumia.frame.pipeline.context.PipelineContextView;
import org.jetbrains.annotations.NotNull;

public final class ServiceCreateRender
    implements PipelineService<PipelineContextView.CreateRender, ContextRender> {

    public static final PipelineService<PipelineContextView.CreateRender, ContextRender> INSTANCE =
        new ServiceCreateRender();

    public static final String KEY = "create";

    @NotNull
    @Override
    public CompletableFuture<ContextRender> handle(
        @NotNull final PipelineContextView.CreateRender ctx
    ) {
        return CompletableFuture.completedFuture(
            new ContextRenderImpl(ctx.context(), ctx.container(), ctx.config(), ctx.layouts())
        );
    }

    @NotNull
    @Override
    public String key() {
        return ServiceCreateRender.KEY;
    }

    private ServiceCreateRender() {}
}
