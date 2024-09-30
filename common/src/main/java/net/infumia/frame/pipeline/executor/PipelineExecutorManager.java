package net.infumia.frame.pipeline.executor;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.infumia.frame.pipeline.context.PipelineContextManager;
import net.infumia.frame.service.ConsumerService;
import net.infumia.frame.service.Implementation;
import net.infumia.frame.typedkey.TypedKeyStorageImmutableBuilder;
import net.infumia.frame.view.View;
import org.jetbrains.annotations.NotNull;

public interface PipelineExecutorManager {
    @NotNull
    CompletableFuture<Collection<Object>> executeViewCreated(
        @NotNull Collection<Class<?>> registeredViews
    );

    @NotNull
    CompletableFuture<Collection<View>> executeViewRegistered(
        @NotNull Collection<Object> registeredViews,
        @NotNull Consumer<TypedKeyStorageImmutableBuilder> instanceConfigurer
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
