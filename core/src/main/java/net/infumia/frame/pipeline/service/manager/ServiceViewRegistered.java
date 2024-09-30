package net.infumia.frame.pipeline.service.manager;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import net.infumia.frame.FrameRich;
import net.infumia.frame.config.ViewConfigBuilderRich;
import net.infumia.frame.context.view.ContextInitImpl;
import net.infumia.frame.pipeline.PipelineService;
import net.infumia.frame.pipeline.context.PipelineContextManager;
import net.infumia.frame.state.StateRegistry;
import net.infumia.frame.view.View;
import net.infumia.frame.view.ViewImpl;
import net.infumia.frame.view.ViewRich;
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
        final FrameRich manager = (FrameRich) ctx.manager();
        final CompletableFuture<View>[] futures = ctx
            .registeredViews()
            .stream()
            .map(instance -> {
                final ViewRich view = new ViewImpl(
                    new ContextInitImpl(
                        manager,
                        manager.storageFactory().create(new ConcurrentHashMap<>()),
                        new StateRegistry(manager.logger()),
                        ViewConfigBuilderRich.create()
                    ),
                    instance
                );
                return view.simulateOnInit().thenApply(state -> view);
            })
            .toArray(CompletableFuture[]::new);
        return CompletableFuture.allOf(futures).thenApply(__ ->
            Arrays.stream(futures).map(CompletableFuture::join).collect(Collectors.toSet())
        );
    }

    private ServiceViewRegistered() {}
}
