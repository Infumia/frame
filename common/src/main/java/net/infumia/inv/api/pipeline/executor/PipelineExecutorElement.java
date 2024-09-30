package net.infumia.inv.api.pipeline.executor;

import java.util.concurrent.CompletableFuture;
import net.infumia.inv.api.context.view.ContextClick;
import net.infumia.inv.api.context.view.ContextRender;
import net.infumia.inv.api.pipeline.context.PipelineContextElement;
import net.infumia.inv.api.service.ConsumerService;
import net.infumia.inv.api.service.Implementation;
import org.jetbrains.annotations.NotNull;

public interface PipelineExecutorElement {
    @NotNull
    CompletableFuture<ConsumerService.State> executeRender(@NotNull ContextRender context);

    @NotNull
    CompletableFuture<ConsumerService.State> executeUpdate(
        @NotNull ContextRender context,
        boolean forced
    );

    @NotNull
    CompletableFuture<ConsumerService.State> executeClick(@NotNull ContextClick context);

    @NotNull
    CompletableFuture<ConsumerService.State> executeClear(@NotNull ContextRender context);

    void applyRender(
        @NotNull Implementation<PipelineContextElement.Render, ConsumerService.State> implementation
    );

    void applyUpdate(
        @NotNull Implementation<PipelineContextElement.Update, ConsumerService.State> implementation
    );

    void applyClick(
        @NotNull Implementation<PipelineContextElement.Click, ConsumerService.State> implementation
    );

    void applyClear(
        @NotNull Implementation<PipelineContextElement.Clear, ConsumerService.State> implementation
    );
}
