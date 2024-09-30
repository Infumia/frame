package net.infumia.inv.core.pipeline.holder;

import io.leangen.geantyref.TypeToken;
import java.util.Collection;
import net.infumia.inv.api.pipeline.Pipeline;
import net.infumia.inv.api.pipeline.PipelineConsumer;
import net.infumia.inv.api.pipeline.PipelineService;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextManager;
import net.infumia.inv.api.view.View;
import net.infumia.inv.core.pipeline.PipelineConsumerImpl;
import net.infumia.inv.core.pipeline.PipelineImpl;
import net.infumia.inv.core.pipeline.service.manager.ServiceListenerRegistered;
import net.infumia.inv.core.pipeline.service.manager.ServiceListenerRegisteredLogging;
import net.infumia.inv.core.pipeline.service.manager.ServiceViewCreated;
import net.infumia.inv.core.pipeline.service.manager.ServiceViewRegistered;
import net.infumia.inv.core.pipeline.service.manager.ServiceViewUnregisteredLogging;
import org.jetbrains.annotations.NotNull;

public final class PipelineHolderManager {

    private final Pipeline<PipelineContextManager.ViewCreated, Collection<Object>> viewCreated;
    private final Pipeline<PipelineContextManager.ViewRegistered, Collection<View>> viewRegistered;
    private final PipelineConsumer<PipelineContextManager.ListenerRegistered> listenersRegistered;
    private final PipelineConsumer<PipelineContextManager.ViewUnregistered> viewUnregistered;

    public static final PipelineHolderManager BASE = new PipelineHolderManager(
        new PipelineImpl<>(
            new TypeToken<
                PipelineService<PipelineContextManager.ViewCreated, Collection<Object>>
            >() {},
            ServiceViewCreated.INSTANCE
        ),
        new PipelineImpl<>(
            new TypeToken<
                PipelineService<PipelineContextManager.ViewRegistered, Collection<View>>
            >() {},
            ServiceViewRegistered.INSTANCE
        ),
        new PipelineConsumerImpl<>(
            new TypeToken<PipelineServiceConsumer<PipelineContextManager.ListenerRegistered>>() {},
            ServiceListenerRegisteredLogging.INSTANCE
        ).register(ServiceListenerRegistered.INSTANCE),
        new PipelineConsumerImpl<>(
            new TypeToken<PipelineServiceConsumer<PipelineContextManager.ViewUnregistered>>() {},
            ServiceViewUnregisteredLogging.INSTANCE
        )
    );

    @NotNull
    public Pipeline<PipelineContextManager.ViewCreated, Collection<Object>> viewCreated() {
        return this.viewCreated;
    }

    @NotNull
    public Pipeline<PipelineContextManager.ViewRegistered, Collection<View>> viewRegistered() {
        return this.viewRegistered;
    }

    @NotNull
    public PipelineConsumer<PipelineContextManager.ListenerRegistered> listenersRegistered() {
        return this.listenersRegistered;
    }

    @NotNull
    public PipelineConsumer<PipelineContextManager.ViewUnregistered> viewUnregistered() {
        return this.viewUnregistered;
    }

    @NotNull
    public PipelineHolderManager createNew() {
        return new PipelineHolderManager(
            this.viewCreated,
            this.viewRegistered,
            this.listenersRegistered,
            this.viewUnregistered
        );
    }

    public PipelineHolderManager(
        @NotNull final Pipeline<PipelineContextManager.ViewCreated, Collection<Object>> viewCreated,
        @NotNull final Pipeline<
            PipelineContextManager.ViewRegistered,
            Collection<View>
        > viewRegistered,
        @NotNull final PipelineConsumer<
            PipelineContextManager.ListenerRegistered
        > listenersRegistered,
        @NotNull final PipelineConsumer<PipelineContextManager.ViewUnregistered> viewUnregistered
    ) {
        this.viewCreated = viewCreated;
        this.viewRegistered = viewRegistered;
        this.listenersRegistered = listenersRegistered;
        this.viewUnregistered = viewUnregistered;
    }
}
