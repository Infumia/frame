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

public final class PipelineExecutorFrameImpl implements PipelineExecutorFrame {

    private final PipelineHolderFrame pipelines = PipelineHolderFrame.BASE.createNew();
    private final Frame frame;

    public PipelineExecutorFrameImpl(@NotNull final Frame frame) {
        this.frame = frame;
    }

    @NotNull
    @Override
    public CompletableFuture<Collection<Object>> executeViewCreated(
        @NotNull final Collection<Class<?>> registeredViews
    ) {
        return this.pipelines.viewCreated()
            .completeWith(
                new PipelineContextFrames.ViewCreated(
                    this.frame,
                    Collections.unmodifiableCollection(registeredViews)
                )
            );
    }

    @NotNull
    @Override
    public CompletableFuture<Collection<View>> executeViewRegistered(
        @NotNull final Collection<Object> registeredViews,
        @NotNull final Consumer<TypedKeyStorageImmutableBuilder> instanceConfigurer
    ) {
        return this.pipelines.viewRegistered()
            .completeWith(
                new PipelineContextFrames.ViewRegistered(
                    this.frame,
                    Collections.unmodifiableCollection(registeredViews),
                    instanceConfigurer
                )
            );
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> executeListenersRegistered() {
        return this.pipelines.listenersRegistered()
            .completeWith(new PipelineContextFrames.ListenerRegistered(this.frame));
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> executeViewUnregistered(
        @NotNull final Collection<View> unregisteredViews
    ) {
        return this.pipelines.viewUnregistered()
            .completeWith(
                new PipelineContextFrames.ViewUnregistered(
                    this.frame,
                    Collections.unmodifiableCollection(unregisteredViews)
                )
            );
    }

    @Override
    public void applyViewCreated(
        @NotNull final Implementation<
            PipelineContextFrame.ViewCreated,
            Collection<Object>
        > implementation
    ) {
        this.pipelines.viewCreated().apply(implementation);
    }

    @Override
    public void applyViewRegistered(
        @NotNull final Implementation<
            PipelineContextFrame.ViewRegistered,
            Collection<View>
        > implementation
    ) {
        this.pipelines.viewRegistered().apply(implementation);
    }

    @Override
    public void applyListenersRegistered(
        @NotNull final Implementation<
            PipelineContextFrame.ListenerRegistered,
            ConsumerService.State
        > implementation
    ) {
        this.pipelines.listenersRegistered().apply(implementation);
    }

    @Override
    public void applyViewUnregistered(
        @NotNull final Implementation<
            PipelineContextFrame.ViewUnregistered,
            ConsumerService.State
        > implementation
    ) {
        this.pipelines.viewUnregistered().apply(implementation);
    }
}
