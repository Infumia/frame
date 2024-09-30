package net.infumia.frame.pipeline.service.view;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import net.infumia.frame.config.ViewConfigBuilderRich;
import net.infumia.frame.context.ContextBase;
import net.infumia.frame.context.ContextBaseImpl;
import net.infumia.frame.context.ContextRich;
import net.infumia.frame.context.view.ContextInit;
import net.infumia.frame.pipeline.PipelineService;
import net.infumia.frame.pipeline.context.PipelineContextView;
import net.infumia.frame.view.View;
import net.infumia.frame.viewer.Viewer;
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
        final View view = ctx.view();
        final ContextInit context = view.context();
        final Collection<Viewer> viewers = ctx.viewers();
        return CompletableFuture.completedFuture(
            new ContextBaseImpl(
                context.manager(),
                context.instances(),
                ((ContextRich) context).stateRegistry(),
                UUID.randomUUID(),
                view,
                ((ViewConfigBuilderRich) context.configBuilder()).build(),
                viewers,
                ctx.initialData(),
                viewers.size() == 1 ? viewers.iterator().next() : null
            )
        );
    }

    private ServiceCreateContext() {}
}
