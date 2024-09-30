package net.infumia.inv.core.pipeline.holder;

import io.leangen.geantyref.TypeToken;
import net.infumia.inv.api.pipeline.PipelineConsumer;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextState;
import net.infumia.inv.core.pipeline.PipelineConsumerImpl;
import net.infumia.inv.core.pipeline.service.state.ServiceAccessLogging;
import net.infumia.inv.core.pipeline.service.state.ServiceUpdateLogging;
import org.jetbrains.annotations.NotNull;

public final class PipelineHolderState {

    private final PipelineConsumer<PipelineContextState.Access> access;
    private final PipelineConsumer<PipelineContextState.Update> update;

    public static final PipelineHolderState BASE = new PipelineHolderState(
        new PipelineConsumerImpl<>(
            new TypeToken<PipelineServiceConsumer<PipelineContextState.Access>>() {},
            ServiceAccessLogging.INSTANCE
        ),
        new PipelineConsumerImpl<>(
            new TypeToken<PipelineServiceConsumer<PipelineContextState.Update>>() {},
            ServiceUpdateLogging.INSTANCE
        )
    );

    @NotNull
    public PipelineConsumer<PipelineContextState.Access> access() {
        return this.access;
    }

    @NotNull
    public PipelineConsumer<PipelineContextState.Update> update() {
        return this.update;
    }

    @NotNull
    public PipelineHolderState createNew() {
        return new PipelineHolderState(this.access, this.update);
    }

    public PipelineHolderState(
        @NotNull final PipelineConsumer<PipelineContextState.Access> access,
        @NotNull final PipelineConsumer<PipelineContextState.Update> update
    ) {
        this.access = access;
        this.update = update;
    }
}
