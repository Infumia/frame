package net.infumia.frame.pipeline.service.frame;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import net.infumia.frame.pipeline.PipelineService;
import net.infumia.frame.pipeline.context.PipelineContextFrame;
import org.jetbrains.annotations.NotNull;

public final class ServiceCreateViews
    implements PipelineService<PipelineContextFrame.CreateViews, Collection<Object>> {

    public static final PipelineService<
        PipelineContextFrame.CreateViews,
        Collection<Object>
    > INSTANCE = new ServiceCreateViews();

    public static final String KEY = "create";

    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    public CompletableFuture<Collection<Object>> handle(
        @NotNull final PipelineContextFrame.CreateViews ctx
    ) {
        final Collection<Class<?>> classes = ctx.rawViews();
        final CompletableFuture<Object>[] created = classes
            .stream()
            .map(ctx.frame().viewFactory()::create)
            .toArray(CompletableFuture[]::new);
        return CompletableFuture.allOf(created).thenApply(__ -> {
                final Collection<Object> views = Arrays.stream(created)
                    .map(CompletableFuture::join)
                    .collect(Collectors.toSet());
                ctx.frame().logger().debug("View classes are created '%s'", classes);
                return views;
            });
    }

    @NotNull
    @Override
    public String key() {
        return ServiceCreateViews.KEY;
    }

    private ServiceCreateViews() {}
}
