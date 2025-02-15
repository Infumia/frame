package net.infumia.frame.view;

import net.infumia.frame.context.view.ContextInit;
import net.infumia.frame.pipeline.Pipelined;
import net.infumia.frame.pipeline.executor.PipelineExecutorView;
import org.jetbrains.annotations.NotNull;

public interface View extends Pipelined<PipelineExecutorView> {
    @NotNull
    ContextInit context();

    @NotNull
    Object instance();
}
