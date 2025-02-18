package net.infumia.frame.pipeline;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.Cloned;
import net.infumia.frame.service.Implementation;
import net.infumia.frame.service.Service;
import org.jetbrains.annotations.NotNull;

public interface PipelineBase<Context, Result, Self extends PipelineBase<Context, Result, Self>>
    extends Cloned<Self> {
    @NotNull
    Self apply(@NotNull Implementation<Context, Result> operation);

    @NotNull
    CompletableFuture<Result> completeWith(@NotNull Context context);

    @NotNull
    CompletableFuture<Result> completeWithAsync(@NotNull Context context);

    @NotNull
    default Self register(@NotNull final Service<Context, Result> service) {
        return this.apply(Implementation.register(service));
    }
}
