package net.infumia.frame.pipeline.service.manager;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import net.infumia.frame.Frame;
import net.infumia.frame.config.ViewConfigBuilderRich;
import net.infumia.frame.context.ContextImpl;
import net.infumia.frame.context.view.ContextInitImpl;
import net.infumia.frame.pipeline.PipelineService;
import net.infumia.frame.pipeline.context.PipelineContextManager;
import net.infumia.frame.state.StateRegistry;
import net.infumia.frame.view.View;
import net.infumia.frame.view.ViewEventHandler;
import net.infumia.frame.view.ViewImpl;
import org.jetbrains.annotations.NotNull;

public final class ServiceViewRegistered
    implements PipelineService<PipelineContextManager.ViewRegistered, Collection<View>> {

    public static final PipelineService<
        PipelineContextManager.ViewRegistered,
        Collection<View>
    > INSTANCE = new ServiceViewRegistered();

    public static final String KEY = "register";

    @Override
    public String key() {
        return ServiceViewRegistered.KEY;
    }

    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    public CompletableFuture<Collection<View>> handle(
        @NotNull final PipelineContextManager.ViewRegistered ctx
    ) {
        final Frame frame = ctx.frame();
        final CompletableFuture<View>[] futures = ctx
            .registeredViews()
            .stream()
            .map(instance -> {
                final View view = new ViewImpl(
                    new ContextInitImpl(
                        new ContextImpl(
                            frame,
                            frame.storageFactory().create(new ConcurrentHashMap<>()),
                            new StateRegistry(frame.logger())
                        ),
                        ViewConfigBuilderRich.create()
                    ),
                    instance
                );
                return ((ViewEventHandler) view).simulateOnInit().thenApply(state -> view);
            })
            .toArray(CompletableFuture[]::new);
        return CompletableFuture.allOf(futures).thenApply(__ ->
            Arrays.stream(futures).map(CompletableFuture::join).collect(Collectors.toSet())
        );
    }

    private ServiceViewRegistered() {}
}
