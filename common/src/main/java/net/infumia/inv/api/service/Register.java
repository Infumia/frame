package net.infumia.inv.api.service;

import java.util.Collection;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class Register<Context, Result> implements Implementation<Context, Result> {

    private final Service<Context, Result> service;
    private final Collection<Predicate<Context>> filters;

    Register(
        @NotNull final Service<Context, Result> service,
        @Nullable final Collection<Predicate<Context>> filters
    ) {
        this.service = service;
        this.filters = filters;
    }

    @Override
    public void handle(@NotNull final ServiceRepository<Context, Result> repository) {
        repository.implementations.add(
            new ServiceWrapper<>(repository.serviceType, this.service, false, this.filters)
        );
    }
}
