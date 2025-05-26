package net.infumia.frame.service;

import java.util.Collection;
import java.util.function.Predicate;

final class Register<Context, Result> implements Implementation<Context, Result> {

    final Service<Context, Result> service;
    final Collection<Predicate<Context>> filters;

    Register(final Service<Context, Result> service, final Collection<Predicate<Context>> filters) {
        this.service = service;
        this.filters = filters;
    }

    @Override
    public void handle(final ServiceRepository<Context, Result> repository) {
        repository.implementations.add(
            new ServiceWrapper<>(repository.serviceType, this.service, false, this.filters)
        );
    }
}
