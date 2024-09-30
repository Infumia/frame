package net.infumia.inv.core.element;

import java.util.concurrent.CompletableFuture;
import net.infumia.inv.api.pipeline.context.PipelineContextElement;
import net.infumia.inv.api.service.ConsumerService;
import org.jetbrains.annotations.NotNull;

public interface ElementEventHandler {
    @NotNull
    CompletableFuture<ConsumerService.State> handleRender(
        @NotNull PipelineContextElement.Render ctx
    );

    @NotNull
    CompletableFuture<ConsumerService.State> handleClear(@NotNull PipelineContextElement.Clear ctx);

    @NotNull
    CompletableFuture<ConsumerService.State> handleClick(@NotNull PipelineContextElement.Click ctx);

    @NotNull
    CompletableFuture<ConsumerService.State> handleUpdate(
        @NotNull PipelineContextElement.Update ctx
    );
}
