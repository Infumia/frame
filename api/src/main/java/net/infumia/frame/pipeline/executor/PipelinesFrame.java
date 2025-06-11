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
    CompletableFuture<Collection<Object>> executeCreateViews(
        @NotNull Collection<Class<?>> rawViews
    );

    @NotNull
    CompletableFuture<Collection<View>> executeRegisterViews(
        @NotNull Collection<Object> views,
        @NotNull Consumer<TypedKeyStorageImmutableBuilder> instanceConfigurer
    );

    @NotNull
    CompletableFuture<ConsumerService.State> executeRegisterListeners();

    @NotNull
    CompletableFuture<ConsumerService.State> executeUnregisterViews(
        @NotNull Collection<View> views
    );

    void applyCreateViews(
        @NotNull Implementation<PipelineContextFrame.CreateViews, Collection<Object>> implementation
    );

    void applyRegisterViews(
        @NotNull Implementation<PipelineContextFrame.RegisterViews, Collection<View>> implementation
    );

    void applyRegisterListeners(
        @NotNull Implementation<
            PipelineContextFrame.RegisterListeners,
            ConsumerService.State
        > implementation
    );

    void applyUnregisterViews(
        @NotNull Implementation<
            PipelineContextFrame.UnregisterViews,
            ConsumerService.State
        > implementation
    );
}
