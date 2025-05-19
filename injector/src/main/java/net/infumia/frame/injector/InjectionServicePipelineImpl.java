package net.infumia.frame.injector;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import net.infumia.frame.service.Implementation;
import net.infumia.frame.service.ServiceRepository;

final class InjectionServicePipelineImpl<C> implements InjectionServicePipeline<C> {

    private final ServiceRepository<InjectionRequest<C>, Object> delegate;

    InjectionServicePipelineImpl(final ServiceRepository<InjectionRequest<C>, Object> delegate) {
        this.delegate = Objects.requireNonNull(delegate, "delegate");
    }

    @Override
    public void apply(final Implementation<InjectionRequest<C>, Object> operation) {
        Objects.requireNonNull(operation, "operation");

        this.delegate.apply(operation);
    }

    @Override
    public CompletableFuture<Object> completeWith(final InjectionRequest<C> request) {
        Objects.requireNonNull(request, "request");

        return this.delegate.completeWith(request);
    }

    @Override
    public CompletableFuture<Object> completeWithAsync(final InjectionRequest<C> request) {
        Objects.requireNonNull(request, "request");

        return this.delegate.completeWithAsync(request);
    }
}
