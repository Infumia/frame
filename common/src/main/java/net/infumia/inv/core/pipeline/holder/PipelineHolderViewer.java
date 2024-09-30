package net.infumia.inv.core.pipeline.holder;

import io.leangen.geantyref.TypeToken;
import net.infumia.inv.api.pipeline.PipelineConsumer;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextViewer;
import net.infumia.inv.core.pipeline.PipelineConsumerImpl;
import net.infumia.inv.core.pipeline.service.viewer.ServiceAdded;
import net.infumia.inv.core.pipeline.service.viewer.ServiceAddedContextualViewer;
import net.infumia.inv.core.pipeline.service.viewer.ServiceAddedLogging;
import net.infumia.inv.core.pipeline.service.viewer.ServiceAddedOnViewerAdded;
import net.infumia.inv.core.pipeline.service.viewer.ServiceRemoved;
import net.infumia.inv.core.pipeline.service.viewer.ServiceRemovedContextualViewer;
import net.infumia.inv.core.pipeline.service.viewer.ServiceRemovedLogging;
import net.infumia.inv.core.pipeline.service.viewer.ServiceRemovedOnViewerRemoved;
import net.infumia.inv.core.pipeline.service.viewer.ServiceRemovedStopUpdateTask;
import org.jetbrains.annotations.NotNull;

public final class PipelineHolderViewer {

    private final PipelineConsumer<PipelineContextViewer.Added> added;
    private final PipelineConsumer<PipelineContextViewer.Removed> removed;

    public static final PipelineHolderViewer BASE = new PipelineHolderViewer(
        new PipelineConsumerImpl<>(
            new TypeToken<PipelineServiceConsumer<PipelineContextViewer.Added>>() {},
            ServiceAddedLogging.INSTANCE
        )
            .register(ServiceAddedOnViewerAdded.INSTANCE)
            .register(ServiceAdded.INSTANCE)
            .register(ServiceAddedContextualViewer.INSTANCE),
        new PipelineConsumerImpl<>(
            new TypeToken<PipelineServiceConsumer<PipelineContextViewer.Removed>>() {},
            ServiceRemovedLogging.INSTANCE
        )
            .register(ServiceRemovedStopUpdateTask.INSTANCE)
            .register(ServiceRemovedContextualViewer.INSTANCE)
            .register(ServiceRemoved.INSTANCE)
            .register(ServiceRemovedOnViewerRemoved.INSTANCE)
    );

    @NotNull
    public PipelineConsumer<PipelineContextViewer.Added> added() {
        return this.added;
    }

    @NotNull
    public PipelineConsumer<PipelineContextViewer.Removed> removed() {
        return this.removed;
    }

    @NotNull
    public PipelineHolderViewer createNew() {
        return new PipelineHolderViewer(this.added, this.removed);
    }

    public PipelineHolderViewer(
        @NotNull final PipelineConsumer<PipelineContextViewer.Added> added,
        @NotNull final PipelineConsumer<PipelineContextViewer.Removed> removed
    ) {
        this.added = added;
        this.removed = removed;
    }
}
