package net.infumia.frame.element;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.pipeline.context.PipelineContextElement;
import org.jetbrains.annotations.NotNull;

public interface ElementEventHandler {
    @NotNull
    CompletableFuture<?> handleRender(@NotNull PipelineContextElement.Render ctx);

    @NotNull
    CompletableFuture<?> handleClear(@NotNull PipelineContextElement.Clear ctx);

    @NotNull
    CompletableFuture<?> handleClick(@NotNull PipelineContextElement.Click ctx);

    @NotNull
    CompletableFuture<?> handleUpdate(@NotNull PipelineContextElement.Update ctx);
}
