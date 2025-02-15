package net.infumia.frame.pipeline;

import org.jetbrains.annotations.NotNull;

public interface Pipelined<P> {
    @NotNull
    P pipelines();
}
