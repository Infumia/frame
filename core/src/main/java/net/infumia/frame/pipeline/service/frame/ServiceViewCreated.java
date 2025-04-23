package net.infumia.frame.pipeline.service.frame;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import net.infumia.frame.pipeline.PipelineService;
import net.infumia.frame.pipeline.context.PipelineContextFrame;
import org.jetbrains.annotations.NotNull;

public final class ServiceViewCreated
    implements PipelineService<PipelineContextFrame.ViewCreated, Collection<Object>> {

    public static final PipelineService<
        PipelineContextFrame.ViewCreated,
        Collection<Object>
    > INSTANCE = new ServiceViewCreated();

    public static final String KEY = "create";

    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    public CompletableFuture<Collection<Object>> handle(
        @NotNull final PipelineContextFrame.ViewCreated ctx
    ) {
        final CompletableFuture<Object>[] created = ctx
            .registeredViews()
            .stream()
            .map(ctx.frame().viewCreator()::create)
            .toArray(CompletableFuture[]::new);
        return CompletableFuture.allOf(created).thenApply(__ -> {
            final Collection<Object> views = Arrays.stream(created)
                .map(CompletableFuture::join)
                .collect(Collectors.toSet());
            ctx.frame().logger().debug("View classes are created '%s'", ctx.registeredViews());
            return views;
        });
    }

    @NotNull
    @Override
    public String key() {
        return ServiceViewCreated.KEY;
    }

    private ServiceViewCreated() {}
}
