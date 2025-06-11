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
    public CompletableFuture<Collection<Object>> executeCreateViews(
        @NotNull final Collection<Class<?>> rawViews
    ) {
        return this.pipelines.createViews()
            .completeWith(
                new PipelineContextFrames.CreateViews(
                    this.frame,
                    Collections.unmodifiableCollection(rawViews)
                )
            );
    }

    @NotNull
    @Override
    public CompletableFuture<Collection<View>> executeRegisterViews(
        @NotNull final Collection<Object> views,
        @NotNull final Consumer<TypedKeyStorageImmutableBuilder> instanceConfigurer
    ) {
        return this.pipelines.registerViews()
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
    public CompletableFuture<ConsumerService.State> executeRegisterListeners() {
        return this.pipelines.registerListeners()
            .completeWith(new PipelineContextFrames.RegisterListeners(this.frame));
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> executeUnregisterViews(
        @NotNull final Collection<View> views
    ) {
        return this.pipelines.unregisterViews()
            .completeWith(
                new PipelineContextFrames.UnregisterViews(
                    this.frame,
                    Collections.unmodifiableCollection(views)
                )
            );
    }

    @Override
    public void applyCreateViews(
        @NotNull final Implementation<
            PipelineContextFrame.CreateViews,
            Collection<Object>
        > implementation
    ) {
        this.pipelines.createViews().apply(implementation);
    }

    @Override
    public void applyRegisterViews(
        @NotNull final Implementation<
            PipelineContextFrame.RegisterViews,
            Collection<View>
        > implementation
    ) {
        this.pipelines.registerViews().apply(implementation);
    }

    @Override
    public void applyRegisterListeners(
        @NotNull final Implementation<
            PipelineContextFrame.RegisterListeners,
            ConsumerService.State
        > implementation
    ) {
        this.pipelines.registerListeners().apply(implementation);
    }

    @Override
    public void applyUnregisterViews(
        @NotNull final Implementation<
            PipelineContextFrame.UnregisterViews,
            ConsumerService.State
        > implementation
    ) {
        this.pipelines.unregisterViews().apply(implementation);
    }
}
