package net.infumia.inv.api.view;

import net.infumia.inv.api.context.view.ContextInit;
import net.infumia.inv.api.pipeline.Pipelined;
import net.infumia.inv.api.pipeline.executor.PipelineExecutorView;
import org.jetbrains.annotations.NotNull;

public interface View extends Pipelined<PipelineExecutorView> {
    @NotNull
    ContextInit context();

    @NotNull
    Object instance();
}
