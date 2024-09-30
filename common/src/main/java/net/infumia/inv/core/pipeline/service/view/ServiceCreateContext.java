package net.infumia.inv.core.pipeline.service.view;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import net.infumia.inv.api.context.ContextBase;
import net.infumia.inv.api.pipeline.PipelineService;
import net.infumia.inv.api.pipeline.context.PipelineContextView;
import net.infumia.inv.api.viewer.Viewer;
import net.infumia.inv.core.context.ContextBaseImpl;
import net.infumia.inv.core.context.view.ContextInitRich;
import net.infumia.inv.core.view.ViewRich;
import net.infumia.inv.core.viewer.ViewerRich;
import org.jetbrains.annotations.NotNull;

public final class ServiceCreateContext
    implements PipelineService<PipelineContextView.CreateContext, ContextBase> {

    public static final PipelineService<PipelineContextView.CreateContext, ContextBase> INSTANCE =
        new ServiceCreateContext();

    public static final String KEY = "create";

    @Override
    public String key() {
        return ServiceCreateContext.KEY;
    }

    @NotNull
    @Override
    public CompletableFuture<ContextBase> handle(
        @NotNull final PipelineContextView.CreateContext ctx
    ) {
        final ViewRich view = (ViewRich) ctx.view();
        final ContextInitRich context = view.context();
        final Collection<Viewer> viewers = ctx.viewers();
        return CompletableFuture.completedFuture(
            new ContextBaseImpl(
                context.manager(),
                context.instances(),
                context.stateRegistry(),
                UUID.randomUUID(),
                view,
                context.configBuilder().build(),
                viewers,
                ctx.initialData(),
                viewers.size() == 1 ? (ViewerRich) viewers.iterator().next() : null
            )
        );
    }

    private ServiceCreateContext() {}
}
