package net.infumia.frame.pipeline.holder;

import io.leangen.geantyref.TypeToken;
import java.util.Collection;
import net.infumia.frame.Cloned;
import net.infumia.frame.pipeline.Pipeline;
import net.infumia.frame.pipeline.PipelineConsumer;
import net.infumia.frame.pipeline.PipelineConsumerImpl;
import net.infumia.frame.pipeline.PipelineImpl;
import net.infumia.frame.pipeline.PipelineService;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextFrame;
import net.infumia.frame.pipeline.service.frame.ServiceCreateViews;
import net.infumia.frame.pipeline.service.frame.ServiceRegisterListeners;
import net.infumia.frame.pipeline.service.frame.ServiceRegisterListenersLogging;
import net.infumia.frame.pipeline.service.frame.ServiceRegisterViews;
import net.infumia.frame.pipeline.service.frame.ServiceUnregisterViewsLogging;
import net.infumia.frame.view.View;
import org.jetbrains.annotations.NotNull;

public final class PipelineHolderFrame implements Cloned<PipelineHolderFrame> {

    private final Pipeline<PipelineContextFrame.CreateViews, Collection<Object>> viewCreated;
    private final Pipeline<PipelineContextFrame.RegisterViews, Collection<View>> viewRegistered;
    private final PipelineConsumer<PipelineContextFrame.RegisterListeners> listenersRegistered;
    private final PipelineConsumer<PipelineContextFrame.UnregisterViews> viewUnregistered;

    public static final PipelineHolderFrame BASE = new PipelineHolderFrame(
        new PipelineImpl<>(
            new TypeToken<
                PipelineService<PipelineContextFrame.CreateViews, Collection<Object>>
            >() {},
            ServiceCreateViews.INSTANCE
        ),
        new PipelineImpl<>(
            new TypeToken<
                PipelineService<PipelineContextFrame.RegisterViews, Collection<View>>
            >() {},
            ServiceRegisterViews.INSTANCE
        ),
        new PipelineConsumerImpl<>(
            new TypeToken<PipelineServiceConsumer<PipelineContextFrame.RegisterListeners>>() {},
            ServiceRegisterListenersLogging.INSTANCE
        ).register(ServiceRegisterListeners.INSTANCE),
        new PipelineConsumerImpl<>(
            new TypeToken<PipelineServiceConsumer<PipelineContextFrame.UnregisterViews>>() {},
            ServiceUnregisterViewsLogging.INSTANCE
        )
    );

    @NotNull
    public Pipeline<PipelineContextFrame.CreateViews, Collection<Object>> viewCreated() {
        return this.viewCreated;
    }

    @NotNull
    public Pipeline<PipelineContextFrame.RegisterViews, Collection<View>> viewRegistered() {
        return this.viewRegistered;
    }

    @NotNull
    public PipelineConsumer<PipelineContextFrame.RegisterListeners> listenersRegistered() {
        return this.listenersRegistered;
    }

    @NotNull
    public PipelineConsumer<PipelineContextFrame.UnregisterViews> viewUnregistered() {
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
        @NotNull final Pipeline<PipelineContextFrame.CreateViews, Collection<Object>> viewCreated,
        @NotNull final Pipeline<
            PipelineContextFrame.RegisterViews,
            Collection<View>
        > viewRegistered,
        @NotNull final PipelineConsumer<PipelineContextFrame.RegisterListeners> listenersRegistered,
        @NotNull final PipelineConsumer<PipelineContextFrame.UnregisterViews> viewUnregistered
    ) {
        this.viewCreated = viewCreated;
        this.viewRegistered = viewRegistered;
        this.listenersRegistered = listenersRegistered;
        this.viewUnregistered = viewUnregistered;
    }
}
