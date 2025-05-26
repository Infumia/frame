package net.infumia.frame.injector;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.Preconditions;
import net.infumia.frame.service.Implementation;
import net.infumia.frame.service.ServiceRepository;

final class InjectionServicePipelineImpl<C> implements InjectionServicePipeline<C> {

    private final ServiceRepository<InjectionRequest<C>, Object> delegate;

    InjectionServicePipelineImpl(final ServiceRepository<InjectionRequest<C>, Object> delegate) {
        this.delegate = Preconditions.argumentNotNull(delegate, "delegate");
    }

    @Override
    public void apply(final Implementation<InjectionRequest<C>, Object> operation) {
        Preconditions.argumentNotNull(operation, "operation");

        this.delegate.apply(operation);
    }

    @Override
    public CompletableFuture<Object> completeDirect(final InjectionRequest<C> request) {
        Preconditions.argumentNotNull(request, "request");

        return this.delegate.completeDirect(request);
    }

    @Override
    public CompletableFuture<Object> completeAsync(final InjectionRequest<C> request) {
        Preconditions.argumentNotNull(request, "request");

        return this.delegate.completeAsync(request);
    }
}
