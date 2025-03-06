package net.infumia.frame.service;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.infumia.frame.Preconditions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class RegisterAfter<Context, Result> implements Implementation<Context, Result> {

    private final String serviceKey;
    private final Service<Context, Result> service;
    private final Collection<Predicate<Context>> filters;

    RegisterAfter(
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
        for (int index = 0; index < implementations.size(); index++) {
            final ServiceWrapper<Context, Result> wrapper = implementations.get(index);
            final Service<Context, Result> implementation = wrapper.implementation;
            if (implementation.key().equals(this.serviceKey)) {
                Preconditions.argument(
                    !wrapper.defaultImplementation,
                    "You cannot put an implementation before the default implementation! Try to replace it instead."
                );
                implementations.add(
                    index,
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
                    .stream()
                    .map(wrapper -> wrapper.implementation)
                    .map(Service::key)
                    .collect(Collectors.toSet())
            )
        );
    }
}
