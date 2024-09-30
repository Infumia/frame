package net.infumia.inv.core.pipeline.holder;

import io.leangen.geantyref.TypeToken;
import net.infumia.inv.api.pipeline.PipelineConsumer;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextRender;
import net.infumia.inv.api.pipeline.context.PipelineContextView;
import net.infumia.inv.core.pipeline.PipelineConsumerImpl;
import net.infumia.inv.core.pipeline.service.render.ServiceFirstRender;
import net.infumia.inv.core.pipeline.service.render.ServiceFirstRenderAvailableSlotResolution;
import net.infumia.inv.core.pipeline.service.render.ServiceFirstRenderConsumeNonRenderedElement;
import net.infumia.inv.core.pipeline.service.render.ServiceFirstRenderInitializeState;
import net.infumia.inv.core.pipeline.service.render.ServiceFirstRenderLayout;
import net.infumia.inv.core.pipeline.service.render.ServiceFirstRenderLogging;
import net.infumia.inv.core.pipeline.service.render.ServiceFirstRenderOnFirstRender;
import net.infumia.inv.core.pipeline.service.render.ServiceFirstRenderPagination;
import net.infumia.inv.core.pipeline.service.render.ServiceFirstRenderWatchState;
import net.infumia.inv.core.pipeline.service.render.ServiceOpenContainer;
import net.infumia.inv.core.pipeline.service.render.ServiceOpenContainerLogging;
import net.infumia.inv.core.pipeline.service.render.ServiceResumeLogging;
import net.infumia.inv.core.pipeline.service.render.ServiceResumeOnResume;
import net.infumia.inv.core.pipeline.service.render.ServiceStartUpdate;
import net.infumia.inv.core.pipeline.service.render.ServiceStartUpdateCancel;
import net.infumia.inv.core.pipeline.service.render.ServiceStartUpdateInvalidate;
import net.infumia.inv.core.pipeline.service.render.ServiceStartUpdateLogging;
import net.infumia.inv.core.pipeline.service.render.ServiceStopUpdate;
import net.infumia.inv.core.pipeline.service.render.ServiceStopUpdateLogging;
import net.infumia.inv.core.pipeline.service.render.ServiceUpdateLogging;
import net.infumia.inv.core.pipeline.service.render.ServiceUpdateOnUpdate;
import net.infumia.inv.core.pipeline.service.view.ServiceTransition;
import net.infumia.inv.core.pipeline.service.view.ServiceTransitionLogging;
import org.jetbrains.annotations.NotNull;

public final class PipelineHolderRender {

    private final PipelineConsumer<PipelineContextRender.FirstRender> firstRender;
    private final PipelineConsumer<PipelineContextView.Transition> transition;
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
            new TypeToken<PipelineServiceConsumer<PipelineContextView.Transition>>() {},
            ServiceTransitionLogging.INSTANCE
        ).register(ServiceTransition.INSTANCE),
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
    public PipelineConsumer<PipelineContextView.Transition> transition() {
        return this.transition;
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
    public PipelineHolderRender createNew() {
        return new PipelineHolderRender(
            this.firstRender,
            this.transition,
            this.openContainer,
            this.resume,
            this.startUpdate,
            this.stopUpdate,
            this.update
        );
    }

    private PipelineHolderRender(
        @NotNull final PipelineConsumer<PipelineContextRender.FirstRender> firstRender,
        @NotNull final PipelineConsumer<PipelineContextView.Transition> transition,
        @NotNull final PipelineConsumer<PipelineContextRender.OpenContainer> openContainer,
        @NotNull final PipelineConsumer<PipelineContextRender.Resume> resume,
        @NotNull final PipelineConsumer<PipelineContextRender.StartUpdate> startUpdate,
        @NotNull final PipelineConsumer<PipelineContextRender.StopUpdate> stopUpdate,
        @NotNull final PipelineConsumer<PipelineContextRender.Update> update
    ) {
        this.firstRender = firstRender;
        this.transition = transition;
        this.openContainer = openContainer;
        this.resume = resume;
        this.startUpdate = startUpdate;
        this.stopUpdate = stopUpdate;
        this.update = update;
    }
}
