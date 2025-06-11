package net.infumia.frame.pipeline.executor;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.infumia.frame.Frame;
import net.infumia.frame.pipeline.context.PipelineContextFrame;
import net.infumia.frame.pipeline.context.PipelineContextFrames;
import net.infumia.frame.pipeline.holder.PipelineHolderFrame;
import net.infumia.frame.service.ConsumerService;
import net.infumia.frame.service.Implementation;
import net.infumia.frame.typedkey.TypedKeyStorageImmutableBuilder;
import net.infumia.frame.view.View;
import org.jetbrains.annotations.NotNull;

public final class PipelinesFrameImpl implements PipelinesFrame {

    private final PipelineHolderFrame pipelines = PipelineHolderFrame.BASE.cloned();
    private final Frame frame;

    public PipelinesFrameImpl(@NotNull final Frame frame) {
        this.frame = frame;
    }

    @NotNull
    @Override
    public CompletableFuture<Collection<Object>> executeViewCreated(
        @NotNull final Collection<Class<?>> rawViews
    ) {
        return this.pipelines.viewCreated()
            .completeWith(
                new PipelineContextFrames.CreateViews(
                    this.frame,
                    Collections.unmodifiableCollection(rawViews)
                )
            );
    }

    @NotNull
    @Override
    public CompletableFuture<Collection<View>> executeViewRegistered(
        @NotNull final Collection<Object> views,
        @NotNull final Consumer<TypedKeyStorageImmutableBuilder> instanceConfigurer
    ) {
        return this.pipelines.viewRegistered()
            .completeWith(
                new PipelineContextFrames.RegisterViews(
                    this.frame,
                    Collections.unmodifiableCollection(views),
                    instanceConfigurer
                )
            );
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> executeListenersRegistered() {
        return this.pipelines.listenersRegistered()
            .completeWith(new PipelineContextFrames.RegisterListeners(this.frame));
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> executeViewUnregistered(
        @NotNull final Collection<View> views
    ) {
        return this.pipelines.viewUnregistered()
            .completeWith(
                new PipelineContextFrames.UnregisterViews(
                    this.frame,
                    Collections.unmodifiableCollection(views)
                )
            );
    }

    @Override
    public void applyViewCreated(
        @NotNull final Implementation<
            PipelineContextFrame.CreateViews,
            Collection<Object>
        > implementation
    ) {
        this.pipelines.viewCreated().apply(implementation);
    }

    @Override
    public void applyViewRegistered(
        @NotNull final Implementation<
            PipelineContextFrame.RegisterViews,
            Collection<View>
        > implementation
    ) {
        this.pipelines.viewRegistered().apply(implementation);
    }

    @Override
    public void applyListenersRegistered(
        @NotNull final Implementation<
            PipelineContextFrame.RegisterListeners,
            ConsumerService.State
        > implementation
    ) {
        this.pipelines.listenersRegistered().apply(implementation);
    }

    @Override
    public void applyViewUnregistered(
        @NotNull final Implementation<
            PipelineContextFrame.UnregisterViews,
            ConsumerService.State
        > implementation
    ) {
        this.pipelines.viewUnregistered().apply(implementation);
    }
}
