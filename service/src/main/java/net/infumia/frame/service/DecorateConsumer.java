package net.infumia.frame.service;

import java.util.Collection;
import java.util.ListIterator;
import java.util.function.Predicate;

// TODO: Maybe DecorateFirst
final class DecorateConsumer<Context> implements Implementation<Context, ConsumerService.State> {

    final ConsumerService<Context> service;
    final Collection<Predicate<Context>> filters;

    DecorateConsumer(
        final ConsumerService<Context> service,
        final Collection<Predicate<Context>> filters
    ) {
        this.service = service;
        this.filters = filters;
    }

    @Override
    public void handle(final ServiceRepository<Context, ConsumerService.State> repository) {
        final ListIterator<ServiceWrapper<Context, ConsumerService.State>> iterator =
            repository.implementations.listIterator();
        if (!iterator.hasNext()) {
            return;
        }
        final ServiceWrapper<Context, ConsumerService.State> service = iterator.next();
        final Service<Context, ConsumerService.State> origin = service.implementation;
        Collection<Predicate<Context>> filters = service.filters;
        if (filters == null) {
            filters = this.filters;
        } else {
            filters.addAll(this.filters);
        }
        iterator.set(
            new ServiceWrapper<>(
                service.serviceType,
                new ServiceDecoratorConsumer<>(origin, this.service),
                service.defaultImplementation,
                filters
            )
        );
    }
}
