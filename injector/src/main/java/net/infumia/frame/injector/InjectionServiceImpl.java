package net.infumia.frame.injector;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import net.infumia.frame.Preconditions;
import org.jetbrains.annotations.NotNull;

final class InjectionServiceImpl<C> implements InjectionService<C> {

    public static final String KEY = "injector";

    private final InjectorRegistry<C> registry;

    InjectionServiceImpl(final InjectorRegistry<C> registry) {
        this.registry = Preconditions.argumentNotNull(registry, "registry");
    }

    @NotNull
    @Override
    public String key() {
        return InjectionServiceImpl.KEY;
    }

    @NotNull
    @Override
    public CompletableFuture<Object> handle(final InjectionRequest<C> request) {
        Preconditions.argumentNotNull(request, "request");

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
