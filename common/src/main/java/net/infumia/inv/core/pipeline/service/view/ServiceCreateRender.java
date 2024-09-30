package net.infumia.inv.core.pipeline.service.view;

import java.util.concurrent.CompletableFuture;
import net.infumia.inv.api.context.view.ContextRender;
import net.infumia.inv.api.pipeline.PipelineService;
import net.infumia.inv.api.pipeline.context.PipelineContextView;
import net.infumia.inv.core.config.ViewConfigRich;
import net.infumia.inv.core.context.ContextBaseRich;
import net.infumia.inv.core.context.view.ContextRenderImpl;
import net.infumia.inv.core.view.ViewContainerRich;
import org.jetbrains.annotations.NotNull;

public final class ServiceCreateRender
    implements PipelineService<PipelineContextView.CreateRender, ContextRender> {

    public static final PipelineService<PipelineContextView.CreateRender, ContextRender> INSTANCE =
        new ServiceCreateRender();

    public static final String KEY = "create";

    @Override
    public String key() {
        return ServiceCreateRender.KEY;
    }

    @NotNull
    @Override
    public CompletableFuture<ContextRender> handle(
        @NotNull final PipelineContextView.CreateRender ctx
    ) {
        return CompletableFuture.completedFuture(
            new ContextRenderImpl(
                (ContextBaseRich) ctx.context(),
                (ViewContainerRich) ctx.container(),
                (ViewConfigRich) ctx.config(),
                ctx.layouts()
            )
        );
    }

    private ServiceCreateRender() {}
}
