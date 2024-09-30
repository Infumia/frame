package net.infumia.inv.api.pipeline.executor;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.infumia.inv.api.pipeline.context.PipelineContextManager;
import net.infumia.inv.api.service.ConsumerService;
import net.infumia.inv.api.service.Implementation;
import net.infumia.inv.api.view.View;
import org.jetbrains.annotations.NotNull;

public interface PipelineExecutorManager {
    @NotNull
    CompletableFuture<Collection<Object>> executeViewCreated(
        @NotNull Collection<Class<?>> registeredViews
    );

    @NotNull
    CompletableFuture<Collection<View>> executeViewRegistered(
        @NotNull Collection<Object> registeredViews
    );

    @NotNull
    CompletableFuture<ConsumerService.State> executeListenersRegistered();

    @NotNull
    CompletableFuture<ConsumerService.State> executeViewUnregistered(
        @NotNull Collection<View> unregisteredViews
    );

    void applyViewCreated(
        @NotNull Implementation<
            PipelineContextManager.ViewCreated,
            Collection<Object>
        > implementation
    );

    void applyViewRegistered(
        @NotNull Implementation<
            PipelineContextManager.ViewRegistered,
            Collection<View>
        > implementation
    );

    void applyListenersRegistered(
        @NotNull Implementation<
            PipelineContextManager.ListenerRegistered,
            ConsumerService.State
        > implementation
    );

    void applyViewUnregistered(
        @NotNull Implementation<
            PipelineContextManager.ViewUnregistered,
            ConsumerService.State
        > implementation
    );
}
