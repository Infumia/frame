package net.infumia.frame.view;

import net.infumia.frame.context.view.ContextInit;
import net.infumia.frame.pipeline.Pipelined;
import net.infumia.frame.pipeline.executor.PipelinesView;
import org.jetbrains.annotations.NotNull;

public interface View extends Pipelined<PipelinesView> {
    @NotNull
    ContextInit context();

    @NotNull
    Object instance();
}
