package net.infumia.inv.api.injection;

import io.leangen.geantyref.TypeToken;
import java.util.concurrent.CompletableFuture;
import net.infumia.inv.api.injector.InjectionRequest;
import net.infumia.inv.api.service.Implementation;
import net.infumia.inv.api.service.ServicePipelineBuilder;
import org.jetbrains.annotations.NotNull;

public interface InjectionServicePipeline<C> {
    @NotNull
    static <C> InjectionServicePipeline<C> create(
        @NotNull final InjectionService<C> defaultService
    ) {
        return new InjectionServicePipelineImpl<>(
            ServicePipelineBuilder.newBuilder()
                .build()
                .create(new TypeToken<InjectionService<C>>() {}, defaultService)
        );
    }

    void apply(@NotNull Implementation<InjectionRequest<C>, Object> operation);

    @NotNull
    CompletableFuture<Object> completeWith(@NotNull InjectionRequest<C> request);

    @NotNull
    CompletableFuture<Object> completeWithAsync(@NotNull InjectionRequest<C> request);
}
