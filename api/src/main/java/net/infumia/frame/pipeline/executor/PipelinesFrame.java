package net.infumia.frame.pipeline.executor;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.infumia.frame.pipeline.context.PipelineContextFrame;
import net.infumia.frame.service.ConsumerService;
import net.infumia.frame.service.Implementation;
import net.infumia.frame.typedkey.TypedKeyStorageImmutableBuilder;
import net.infumia.frame.view.View;
import org.jetbrains.annotations.NotNull;

public interface PipelinesFrame {
    @NotNull
    CompletableFuture<Collection<Object>> executeViewCreated(
        @NotNull Collection<Class<?>> rawViews
    );

    @NotNull
    CompletableFuture<Collection<View>> executeViewRegistered(
        @NotNull Collection<Object> views,
        @NotNull Consumer<TypedKeyStorageImmutableBuilder> instanceConfigurer
    );

    @NotNull
    CompletableFuture<ConsumerService.State> executeListenersRegistered();

    @NotNull
    CompletableFuture<ConsumerService.State> executeViewUnregistered(
        @NotNull Collection<View> views
    );

    void applyViewCreated(
        @NotNull Implementation<PipelineContextFrame.CreateViews, Collection<Object>> implementation
    );

    void applyViewRegistered(
        @NotNull Implementation<PipelineContextFrame.RegisterViews, Collection<View>> implementation
    );

    void applyListenersRegistered(
        @NotNull Implementation<
            PipelineContextFrame.RegisterListeners,
            ConsumerService.State
        > implementation
    );

    void applyViewUnregistered(
        @NotNull Implementation<
            PipelineContextFrame.UnregisterViews,
            ConsumerService.State
        > implementation
    );
}
