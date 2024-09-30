package net.infumia.frame.pipeline.executor;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import net.infumia.frame.Frame;
import net.infumia.frame.pipeline.context.PipelineContextManager;
import net.infumia.frame.pipeline.context.PipelineContextManagers;
import net.infumia.frame.pipeline.holder.PipelineHolderManager;
import net.infumia.frame.service.ConsumerService;
import net.infumia.frame.service.Implementation;
import net.infumia.frame.view.View;
import org.jetbrains.annotations.NotNull;

public final class PipelineExecutorManagerImpl implements PipelineExecutorManager {

    private final PipelineHolderManager pipelines = PipelineHolderManager.BASE.createNew();
    private final Frame manager;

    public PipelineExecutorManagerImpl(@NotNull final Frame manager) {
        this.manager = manager;
    }

    @NotNull
    @Override
    public CompletableFuture<Collection<Object>> executeViewCreated(
        @NotNull final Collection<Class<?>> registeredViews
    ) {
        return this.pipelines.viewCreated()
            .completeWith(
                new PipelineContextManagers.ViewCreated(
                    this.manager,
                    Collections.unmodifiableCollection(registeredViews)
                )
            );
    }

    @NotNull
    @Override
    public CompletableFuture<Collection<View>> executeViewRegistered(
        @NotNull final Collection<Object> registeredViews
    ) {
        return this.pipelines.viewRegistered()
            .completeWith(
                new PipelineContextManagers.ViewRegistered(
                    this.manager,
                    Collections.unmodifiableCollection(registeredViews)
                )
            );
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> executeListenersRegistered() {
        return this.pipelines.listenersRegistered()
            .completeWith(new PipelineContextManagers.ListenerRegistered(this.manager));
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> executeViewUnregistered(
        @NotNull final Collection<View> unregisteredViews
    ) {
        return this.pipelines.viewUnregistered()
            .completeWith(
                new PipelineContextManagers.ViewUnregistered(
                    this.manager,
                    Collections.unmodifiableCollection(unregisteredViews)
                )
            );
    }

    @Override
    public void applyViewCreated(
        @NotNull final Implementation<
            PipelineContextManager.ViewCreated,
            Collection<Object>
        > implementation
    ) {
        this.pipelines.viewCreated().apply(implementation);
    }

    @Override
    public void applyViewRegistered(
        @NotNull final Implementation<
            PipelineContextManager.ViewRegistered,
            Collection<View>
        > implementation
    ) {
        this.pipelines.viewRegistered().apply(implementation);
    }

    @Override
    public void applyListenersRegistered(
        @NotNull final Implementation<
            PipelineContextManager.ListenerRegistered,
            ConsumerService.State
        > implementation
    ) {
        this.pipelines.listenersRegistered().apply(implementation);
    }

    @Override
    public void applyViewUnregistered(
        @NotNull final Implementation<
            PipelineContextManager.ViewUnregistered,
            ConsumerService.State
        > implementation
    ) {
        this.pipelines.viewUnregistered().apply(implementation);
    }
}
