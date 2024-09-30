package net.infumia.inv.api.service;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class RegisterBefore<Context, Result> implements Implementation<Context, Result> {

    private final String serviceKey;
    private final Service<Context, Result> service;
    private final Collection<Predicate<Context>> filters;

    RegisterBefore(
        @NotNull final String serviceKey,
        @NotNull final Service<Context, Result> service,
        @Nullable final Collection<Predicate<Context>> filters
    ) {
        this.serviceKey = serviceKey;
        this.service = service;
        this.filters = filters;
    }

    @Override
    public void handle(@NotNull final ServiceRepository<Context, Result> repository) {
        final List<ServiceWrapper<Context, Result>> implementations = repository.implementations;
        final ListIterator<ServiceWrapper<Context, Result>> iterator =
            implementations.listIterator();
        while (iterator.hasNext()) {
            final ServiceWrapper<Context, Result> next = iterator.next();
            if (next.implementation.key().equals(this.serviceKey)) {
                iterator.add(
                    new ServiceWrapper<>(repository.serviceType, this.service, false, this.filters)
                );
                return;
            }
        }
        throw new IllegalArgumentException(
            String.format(
                "Service '%s' not found in the implementation list '%s'!",
                this.serviceKey,
                implementations
            )
        );
    }
}
