package net.infumia.frame.pipeline.holder;

import io.leangen.geantyref.TypeToken;
import net.infumia.frame.pipeline.PipelineConsumer;
import net.infumia.frame.pipeline.PipelineConsumerImpl;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextRender;
import net.infumia.frame.pipeline.context.PipelineContextView;
import net.infumia.frame.pipeline.service.render.ServiceFirstRender;
import net.infumia.frame.pipeline.service.render.ServiceFirstRenderAvailableSlotResolution;
import net.infumia.frame.pipeline.service.render.ServiceFirstRenderConsumeNonRenderedElement;
import net.infumia.frame.pipeline.service.render.ServiceFirstRenderInitializeState;
import net.infumia.frame.pipeline.service.render.ServiceFirstRenderLayout;
import net.infumia.frame.pipeline.service.render.ServiceFirstRenderLogging;
import net.infumia.frame.pipeline.service.render.ServiceFirstRenderOnFirstRender;
import net.infumia.frame.pipeline.service.render.ServiceFirstRenderPagination;
import net.infumia.frame.pipeline.service.render.ServiceFirstRenderWatchState;
import net.infumia.frame.pipeline.service.render.ServiceOpenContainer;
import net.infumia.frame.pipeline.service.render.ServiceOpenContainerLogging;
import net.infumia.frame.pipeline.service.render.ServiceResumeLogging;
import net.infumia.frame.pipeline.service.render.ServiceResumeOnResume;
import net.infumia.frame.pipeline.service.render.ServiceStartUpdate;
import net.infumia.frame.pipeline.service.render.ServiceStartUpdateCancel;
import net.infumia.frame.pipeline.service.render.ServiceStartUpdateInvalidate;
import net.infumia.frame.pipeline.service.render.ServiceStartUpdateLogging;
import net.infumia.frame.pipeline.service.render.ServiceStopUpdate;
import net.infumia.frame.pipeline.service.render.ServiceStopUpdateLogging;
import net.infumia.frame.pipeline.service.render.ServiceUpdateLogging;
import net.infumia.frame.pipeline.service.render.ServiceUpdateOnUpdate;
import net.infumia.frame.pipeline.service.view.ServiceEndTransition;
import net.infumia.frame.pipeline.service.view.ServiceEndTransitionLogging;
import net.infumia.frame.pipeline.service.view.ServiceStartTransition;
import net.infumia.frame.pipeline.service.view.ServiceStartTransitionLogging;
import net.infumia.frame.util.Cloned;
import org.jetbrains.annotations.NotNull;

public final class PipelineHolderRender implements Cloned<PipelineHolderRender> {

    private final PipelineConsumer<PipelineContextRender.FirstRender> firstRender;
    private final PipelineConsumer<PipelineContextView.StartTransition> startTransition;
    private final PipelineConsumer<PipelineContextView.EndTransition> endTransition;
    private final PipelineConsumer<PipelineContextRender.OpenContainer> openContainer;
    private final PipelineConsumer<PipelineContextRender.Resume> resume;
    private final PipelineConsumer<PipelineContextRender.StartUpdate> startUpdate;
    private final PipelineConsumer<PipelineContextRender.StopUpdate> stopUpdate;
    private final PipelineConsumer<PipelineContextRender.Update> update;

    public static final PipelineHolderRender BASE = new PipelineHolderRender(
        new PipelineConsumerImpl<>(
            new TypeToken<PipelineServiceConsumer<PipelineContextRender.FirstRender>>() {},
            ServiceFirstRenderLogging.INSTANCE
        )
            .register(ServiceFirstRender.INSTANCE)
            .register(ServiceFirstRenderWatchState.INSTANCE)
            .register(ServiceFirstRenderConsumeNonRenderedElement.INSTANCE)
            .register(ServiceFirstRenderAvailableSlotResolution.INSTANCE)
            .register(ServiceFirstRenderLayout.INSTANCE)
            .register(ServiceFirstRenderOnFirstRender.INSTANCE)
            .register(ServiceFirstRenderPagination.INSTANCE)
            .register(ServiceFirstRenderInitializeState.INSTANCE),
        new PipelineConsumerImpl<>(
            new TypeToken<PipelineServiceConsumer<PipelineContextView.StartTransition>>() {},
            ServiceStartTransitionLogging.INSTANCE
        ).register(ServiceStartTransition.INSTANCE),
        new PipelineConsumerImpl<>(
            new TypeToken<PipelineServiceConsumer<PipelineContextView.EndTransition>>() {},
            ServiceEndTransitionLogging.INSTANCE
        ).register(ServiceEndTransition.INSTANCE),
        new PipelineConsumerImpl<>(
            new TypeToken<PipelineServiceConsumer<PipelineContextRender.OpenContainer>>() {},
            ServiceOpenContainerLogging.INSTANCE
        ).register(ServiceOpenContainer.INSTANCE),
        new PipelineConsumerImpl<>(
            new TypeToken<PipelineServiceConsumer<PipelineContextRender.Resume>>() {},
            ServiceResumeLogging.INSTANCE
        ).register(ServiceResumeOnResume.INSTANCE),
        new PipelineConsumerImpl<>(
            new TypeToken<PipelineServiceConsumer<PipelineContextRender.StartUpdate>>() {},
            ServiceStartUpdateLogging.INSTANCE
        )
            .register(ServiceStartUpdate.INSTANCE)
            .register(ServiceStartUpdateCancel.INSTANCE)
            .register(ServiceStartUpdateInvalidate.INSTANCE),
        new PipelineConsumerImpl<>(
            new TypeToken<PipelineServiceConsumer<PipelineContextRender.StopUpdate>>() {},
            ServiceStopUpdateLogging.INSTANCE
        ).register(ServiceStopUpdate.INSTANCE),
        new PipelineConsumerImpl<>(
            new TypeToken<PipelineServiceConsumer<PipelineContextRender.Update>>() {},
            ServiceUpdateLogging.INSTANCE
        ).register(ServiceUpdateOnUpdate.INSTANCE)
    );

    @NotNull
    public PipelineConsumer<PipelineContextRender.FirstRender> firstRender() {
        return this.firstRender;
    }

    @NotNull
    public PipelineConsumer<PipelineContextView.StartTransition> startTransition() {
        return this.startTransition;
    }

    @NotNull
    public PipelineConsumer<PipelineContextView.EndTransition> endTransition() {
        return this.endTransition;
    }

    @NotNull
    public PipelineConsumer<PipelineContextRender.OpenContainer> openContainer() {
        return this.openContainer;
    }

    @NotNull
    public PipelineConsumer<PipelineContextRender.Resume> resume() {
        return this.resume;
    }

    @NotNull
    public PipelineConsumer<PipelineContextRender.StartUpdate> startUpdate() {
        return this.startUpdate;
    }

    @NotNull
    public PipelineConsumer<PipelineContextRender.StopUpdate> stopUpdate() {
        return this.stopUpdate;
    }

    @NotNull
    public PipelineConsumer<PipelineContextRender.Update> update() {
        return this.update;
    }

    @NotNull
    @Override
    public PipelineHolderRender cloned() {
        return new PipelineHolderRender(
            this.firstRender.cloned(),
            this.startTransition.cloned(),
            this.endTransition.cloned(),
            this.openContainer.cloned(),
            this.resume.cloned(),
            this.startUpdate.cloned(),
            this.stopUpdate.cloned(),
            this.update.cloned()
        );
    }

    private PipelineHolderRender(
        @NotNull final PipelineConsumer<PipelineContextRender.FirstRender> firstRender,
        @NotNull final PipelineConsumer<PipelineContextView.StartTransition> startTransition,
        @NotNull final PipelineConsumer<PipelineContextView.EndTransition> endTransition,
        @NotNull final PipelineConsumer<PipelineContextRender.OpenContainer> openContainer,
        @NotNull final PipelineConsumer<PipelineContextRender.Resume> resume,
        @NotNull final PipelineConsumer<PipelineContextRender.StartUpdate> startUpdate,
        @NotNull final PipelineConsumer<PipelineContextRender.StopUpdate> stopUpdate,
        @NotNull final PipelineConsumer<PipelineContextRender.Update> update
    ) {
        this.firstRender = firstRender;
        this.startTransition = startTransition;
        this.endTransition = endTransition;
        this.openContainer = openContainer;
        this.resume = resume;
        this.startUpdate = startUpdate;
        this.stopUpdate = stopUpdate;
        this.update = update;
    }
}
