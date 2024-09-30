package net.infumia.inv.api.injection;

import java.util.concurrent.CompletableFuture;
import net.infumia.inv.api.injector.InjectionRequest;
import net.infumia.inv.api.service.Implementation;
import net.infumia.inv.api.service.ServiceRepository;
import org.jetbrains.annotations.NotNull;

final class InjectionServicePipelineImpl<C> implements InjectionServicePipeline<C> {

    @NotNull
    private final ServiceRepository<InjectionRequest<C>, Object> delegate;

    InjectionServicePipelineImpl(
        @NotNull final ServiceRepository<InjectionRequest<C>, Object> delegate
    ) {
        this.delegate = delegate;
    }

    @Override
    public void apply(@NotNull final Implementation<InjectionRequest<C>, Object> operation) {
        this.delegate.apply(operation);
    }

    @NotNull
    @Override
    public CompletableFuture<Object> completeWith(@NotNull final InjectionRequest<C> request) {
        return this.delegate.completeWith(request);
    }

    @NotNull
    @Override
    public CompletableFuture<Object> completeWithAsync(@NotNull final InjectionRequest<C> request) {
        return this.delegate.completeWithAsync(request);
    }
}
