package net.infumia.frame.pipeline.service.manager;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import net.infumia.frame.pipeline.PipelineService;
import net.infumia.frame.pipeline.context.PipelineContextManager;
import org.jetbrains.annotations.NotNull;

public final class ServiceViewCreated
    implements PipelineService<PipelineContextManager.ViewCreated, Collection<Object>> {

    public static final PipelineService<
        PipelineContextManager.ViewCreated,
        Collection<Object>
    > INSTANCE = new ServiceViewCreated();

    public static final String KEY = "create";

    @Override
    public String key() {
        return ServiceViewCreated.KEY;
    }

    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    public CompletableFuture<Collection<Object>> handle(
        @NotNull final PipelineContextManager.ViewCreated ctx
    ) {
        final CompletableFuture<Object>[] created = ctx
            .registeredViews()
            .stream()
            .map(ctx.manager().viewCreator()::create)
            .toArray(CompletableFuture[]::new);
        return CompletableFuture.allOf(created).thenApply(__ -> {
            final Collection<Object> views = Arrays.stream(created)
                .map(CompletableFuture::join)
                .collect(Collectors.toSet());
            ctx.manager().logger().debug("View classes are created '%s'", ctx.registeredViews());
            return views;
        });
    }

    private ServiceViewCreated() {}
}
