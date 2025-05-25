package net.infumia.frame.pipeline;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.Cloned;
import net.infumia.frame.service.Implementation;
import net.infumia.frame.service.Service;
import org.jetbrains.annotations.NotNull;

public interface PipelineBase<Context, Result, This extends PipelineBase<Context, Result, This>>
    extends Cloned<This> {
    @NotNull
    This apply(@NotNull Implementation<Context, Result> operation);

    @NotNull
    CompletableFuture<Result> completeWith(@NotNull Context context);

    @NotNull
    CompletableFuture<Result> completeWithAsync(@NotNull Context context);

    @NotNull
    default This register(@NotNull final Service<Context, Result> service) {
        return this.apply(Implementation.register(service));
    }
}
