package net.infumia.inv.api.injection;

import net.infumia.inv.api.injector.InjectionRequest;
import net.infumia.inv.api.injector.InjectorRegistry;
import net.infumia.inv.api.service.Service;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface InjectionService<C> extends Service<InjectionRequest<C>, @Nullable Object> {
    @NotNull
    static <C> InjectionService<C> create(@NotNull final InjectorRegistry<C> registry) {
        return new InjectionServiceInjector<>(registry);
    }
}
