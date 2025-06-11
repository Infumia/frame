package net.infumia.frame.pipeline.holder;

import io.leangen.geantyref.TypeToken;
import java.util.Collection;
import net.infumia.frame.Cloned;
import net.infumia.frame.pipeline.*;
import net.infumia.frame.pipeline.context.PipelineContextFrame;
import net.infumia.frame.pipeline.service.frame.*;
import net.infumia.frame.view.View;
import org.jetbrains.annotations.NotNull;

public final class PipelineHolderFrame implements Cloned<PipelineHolderFrame> {

    private final Pipeline<PipelineContextFrame.CreateViews, Collection<Object>> createViews;
    private final Pipeline<PipelineContextFrame.RegisterViews, Collection<View>> registerViews;
    private final PipelineConsumer<PipelineContextFrame.RegisterListeners> registerListeners;
    private final PipelineConsumer<PipelineContextFrame.UnregisterViews> unregisterViews;

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
    public Pipeline<PipelineContextFrame.CreateViews, Collection<Object>> createViews() {
        return this.createViews;
    }

    @NotNull
    public Pipeline<PipelineContextFrame.RegisterViews, Collection<View>> registerViews() {
        return this.registerViews;
    }

    @NotNull
    public PipelineConsumer<PipelineContextFrame.RegisterListeners> registerListeners() {
        return this.registerListeners;
    }

    @NotNull
    public PipelineConsumer<PipelineContextFrame.UnregisterViews> unregisterViews() {
        return this.unregisterViews;
    }

    @NotNull
    @Override
    public PipelineHolderFrame cloned() {
        return new PipelineHolderFrame(
            this.createViews.cloned(),
            this.registerViews.cloned(),
            this.registerListeners.cloned(),
            this.unregisterViews.cloned()
        );
    }

    public PipelineHolderFrame(
        @NotNull final Pipeline<PipelineContextFrame.CreateViews, Collection<Object>> createViews,
        @NotNull final Pipeline<PipelineContextFrame.RegisterViews, Collection<View>> registerViews,
        @NotNull final PipelineConsumer<PipelineContextFrame.RegisterListeners> registerListeners,
        @NotNull final PipelineConsumer<PipelineContextFrame.UnregisterViews> unregisterViews
    ) {
        this.createViews = createViews;
        this.registerViews = registerViews;
        this.registerListeners = registerListeners;
        this.unregisterViews = unregisterViews;
    }
}
