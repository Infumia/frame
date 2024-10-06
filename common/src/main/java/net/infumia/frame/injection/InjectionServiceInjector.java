package net.infumia.frame.injection;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import net.infumia.frame.injector.InjectionRequest;
import net.infumia.frame.injector.InjectorRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class InjectionServiceInjector<C> implements InjectionService<C> {

    public static final String KEY = "injector";

    @NotNull
    private final InjectorRegistry<C> registry;

    InjectionServiceInjector(@NotNull final InjectorRegistry<C> registry) {
        this.registry = registry;
    }

    @NotNull
    @Override
    public String key() {
        return InjectionServiceInjector.KEY;
    }

    @NotNull
    @Override
    public CompletableFuture<@Nullable Object> handle(@NotNull final InjectionRequest<C> request) {
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
