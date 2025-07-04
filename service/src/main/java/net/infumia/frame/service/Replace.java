package net.infumia.frame.service;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

final class Replace<Context, Result> implements Implementation<Context, Result> {

    private final String serviceKey;
    private final UnaryOperator<Service<Context, Result>> service;
    private final Collection<Predicate<Context>> filters;

    Replace(
        final String serviceKey,
        final UnaryOperator<Service<Context, Result>> service,
        final Collection<Predicate<Context>> filters
    ) {
        this.serviceKey = serviceKey;
        this.service = service;
        this.filters = filters;
    }

    @Override
    public void handle(final ServiceRepository<Context, Result> repository) {
        final List<ServiceWrapper<Context, Result>> implementations = repository.implementations;
        final ListIterator<ServiceWrapper<Context, Result>> iterator =
            implementations.listIterator();
        while (iterator.hasNext()) {
            final Service<Context, Result> oldImplementation = iterator.next().implementation;
            if (oldImplementation.key().equals(this.serviceKey)) {
                iterator.set(
                    new ServiceWrapper<>(
                        repository.serviceType,
                        this.service.apply(oldImplementation),
                        false,
                        this.filters
                    )
                );
                return;
            }
        }
        throw new IllegalArgumentException(
            String.format(
                "Service '%s' not found in the implementation list '%s'!",
                this.serviceKey,
                implementations
                    .stream()
                    .map(wrapper -> wrapper.implementation)
                    .map(Service::key)
                    .collect(Collectors.toSet())
            )
        );
    }
}
