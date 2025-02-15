package net.infumia.frame.pipeline;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.Cloned;
import net.infumia.frame.service.Implementation;
import net.infumia.frame.service.Service;
import org.jetbrains.annotations.NotNull;

public interface PipelineBase<B extends PipelineContext, R, Self extends PipelineBase<B, R, Self>>
    extends Cloned<Self> {
    @NotNull
    Self apply(@NotNull Implementation<B, R> operation);

    @NotNull
    CompletableFuture<R> completeWith(@NotNull B context);

    @NotNull
    CompletableFuture<R> completeWithAsync(@NotNull B context);

    @NotNull
    default Self register(@NotNull final Service<B, R> service) {
        return this.apply(Implementation.register(service));
    }
}
