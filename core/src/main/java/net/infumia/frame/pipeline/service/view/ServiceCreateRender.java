package net.infumia.frame.pipeline.service.view;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.context.view.ContextRenderImpl;
import net.infumia.frame.pipeline.PipelineService;
import net.infumia.frame.pipeline.context.PipelineContextView;
import net.infumia.frame.view.ViewContainerRich;
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
        final ViewContainerRich container = (ViewContainerRich) ctx.container();
        final ContextRender render = new ContextRenderImpl(
            ctx.context(),
            container,
            ctx.config(),
            ctx.layouts()
        );
        container.inventoryHolder().context(render);
        return CompletableFuture.completedFuture(render);
    }

    @NotNull
    @Override
    public String key() {
        return ServiceCreateRender.KEY;
    }

    private ServiceCreateRender() {}
}
