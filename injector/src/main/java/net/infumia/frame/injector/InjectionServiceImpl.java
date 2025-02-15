package net.infumia.frame.injector;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

final class InjectionServiceImpl<C> implements InjectionService<C> {

    public static final String KEY = "injector";

    private final InjectorRegistry<C> registry;

    InjectionServiceImpl(final InjectorRegistry<C> registry) {
        this.registry = Objects.requireNonNull(registry, "registry");
    }

    @Override
    public String key() {
        return InjectionServiceImpl.KEY;
    }

    @Override
    public CompletableFuture<Object> handle(final InjectionRequest<C> request) {
        Objects.requireNonNull(request, "request");

        return CompletableFuture.completedFuture(
            this.registry.injectors(request.injectedType())
                .stream()
                .map(injector -> injector.inject(request))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null)
        );
    }
}
