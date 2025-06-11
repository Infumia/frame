package net.infumia.frame.service;

import java.util.Collection;
import java.util.ListIterator;
import java.util.function.Predicate;

// TODO: Maybe DecorateFirst
final class Decorate<Context, Result> implements Implementation<Context, Result> {

    final ConsumerService<Context> service;
    final Collection<Predicate<Context>> filters;

    Decorate(final ConsumerService<Context> service, final Collection<Predicate<Context>> filters) {
        this.service = service;
        this.filters = filters;
    }

    @Override
    public void handle(final ServiceRepository<Context, Result> repository) {
        final ListIterator<ServiceWrapper<Context, Result>> iterator =
            repository.implementations.listIterator();
        if (!iterator.hasNext()) {
            return;
        }
        final ServiceWrapper<Context, Result> service = iterator.next();
        Collection<Predicate<Context>> filters = service.filters;
        if (filters == null) {
            filters = this.filters;
        } else {
            filters.addAll(this.filters);
        }
        iterator.set(
            new ServiceWrapper<>(
                service.serviceType,
                new ServiceDecorator<>(service.implementation, this.service),
                service.defaultImplementation,
                filters
            )
        );
    }
}
