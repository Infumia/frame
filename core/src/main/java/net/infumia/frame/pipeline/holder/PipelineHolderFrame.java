package net.infumia.frame.pipeline.holder;

import io.leangen.geantyref.TypeToken;
import java.util.Collection;
import net.infumia.frame.pipeline.Pipeline;
import net.infumia.frame.pipeline.PipelineConsumer;
import net.infumia.frame.pipeline.PipelineConsumerImpl;
import net.infumia.frame.pipeline.PipelineImpl;
import net.infumia.frame.pipeline.PipelineService;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextFrame;
import net.infumia.frame.pipeline.service.frame.ServiceListenerRegistered;
import net.infumia.frame.pipeline.service.frame.ServiceListenerRegisteredLogging;
import net.infumia.frame.pipeline.service.frame.ServiceViewCreated;
import net.infumia.frame.pipeline.service.frame.ServiceViewRegistered;
import net.infumia.frame.pipeline.service.frame.ServiceViewUnregisteredLogging;
import net.infumia.frame.util.Cloned;
import net.infumia.frame.view.View;
import org.jetbrains.annotations.NotNull;

public final class PipelineHolderFrame implements Cloned<PipelineHolderFrame> {

    private final Pipeline<PipelineContextFrame.ViewCreated, Collection<Object>> viewCreated;
    private final Pipeline<PipelineContextFrame.ViewRegistered, Collection<View>> viewRegistered;
    private final PipelineConsumer<PipelineContextFrame.ListenerRegistered> listenersRegistered;
    private final PipelineConsumer<PipelineContextFrame.ViewUnregistered> viewUnregistered;

    public static final PipelineHolderFrame BASE = new PipelineHolderFrame(
        new PipelineImpl<>(
            new TypeToken<
                PipelineService<PipelineContextFrame.ViewCreated, Collection<Object>>
            >() {},
            ServiceViewCreated.INSTANCE
        ),
        new PipelineImpl<>(
            new TypeToken<
                PipelineService<PipelineContextFrame.ViewRegistered, Collection<View>>
            >() {},
            ServiceViewRegistered.INSTANCE
        ),
        new PipelineConsumerImpl<>(
            new TypeToken<PipelineServiceConsumer<PipelineContextFrame.ListenerRegistered>>() {},
            ServiceListenerRegisteredLogging.INSTANCE
        ).register(ServiceListenerRegistered.INSTANCE),
        new PipelineConsumerImpl<>(
            new TypeToken<PipelineServiceConsumer<PipelineContextFrame.ViewUnregistered>>() {},
            ServiceViewUnregisteredLogging.INSTANCE
        )
    );

    @NotNull
    public Pipeline<PipelineContextFrame.ViewCreated, Collection<Object>> viewCreated() {
        return this.viewCreated;
    }

    @NotNull
    public Pipeline<PipelineContextFrame.ViewRegistered, Collection<View>> viewRegistered() {
        return this.viewRegistered;
    }

    @NotNull
    public PipelineConsumer<PipelineContextFrame.ListenerRegistered> listenersRegistered() {
        return this.listenersRegistered;
    }

    @NotNull
    public PipelineConsumer<PipelineContextFrame.ViewUnregistered> viewUnregistered() {
        return this.viewUnregistered;
    }

    @NotNull
    @Override
    public PipelineHolderFrame cloned() {
        return new PipelineHolderFrame(
            this.viewCreated.cloned(),
            this.viewRegistered.cloned(),
            this.listenersRegistered.cloned(),
            this.viewUnregistered.cloned()
        );
    }

    public PipelineHolderFrame(
        @NotNull final Pipeline<PipelineContextFrame.ViewCreated, Collection<Object>> viewCreated,
        @NotNull final Pipeline<
            PipelineContextFrame.ViewRegistered,
            Collection<View>
        > viewRegistered,
        @NotNull final PipelineConsumer<
            PipelineContextFrame.ListenerRegistered
        > listenersRegistered,
        @NotNull final PipelineConsumer<PipelineContextFrame.ViewUnregistered> viewUnregistered
    ) {
        this.viewCreated = viewCreated;
        this.viewRegistered = viewRegistered;
        this.listenersRegistered = listenersRegistered;
        this.viewUnregistered = viewUnregistered;
    }
}
