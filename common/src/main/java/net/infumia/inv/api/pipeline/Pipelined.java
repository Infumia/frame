package net.infumia.inv.api.pipeline;

import org.jetbrains.annotations.NotNull;

public interface Pipelined<P> {
    @NotNull
    P pipelines();
}
