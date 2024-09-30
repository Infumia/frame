package net.infumia.frame.injection;

import net.infumia.frame.injector.InjectionRequest;
import net.infumia.frame.injector.InjectorRegistry;
import net.infumia.frame.service.Service;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface InjectionService<C> extends Service<InjectionRequest<C>, @Nullable Object> {
    @NotNull
    static <C> InjectionService<C> create(@NotNull final InjectorRegistry<C> registry) {
        return new InjectionServiceInjector<>(registry);
    }
}
