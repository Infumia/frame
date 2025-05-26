package net.infumia.frame.injector;

import io.leangen.geantyref.TypeToken;
import java.util.concurrent.CompletableFuture;
import net.infumia.frame.service.Implementation;
import net.infumia.frame.service.ServicePipelineBuilder;

public interface InjectionServicePipeline<C> {
    static <C> InjectionServicePipeline<C> create(final InjectionService<C> defaultService) {
        return new InjectionServicePipelineImpl<>(
            ServicePipelineBuilder.newBuilder()
                .build()
                .create(new TypeToken<InjectionService<C>>() {}, defaultService)
        );
    }

    void apply(Implementation<InjectionRequest<C>, Object> operation);

    CompletableFuture<Object> completeDirect(InjectionRequest<C> request);

    CompletableFuture<Object> completeAsync(InjectionRequest<C> request);
}
