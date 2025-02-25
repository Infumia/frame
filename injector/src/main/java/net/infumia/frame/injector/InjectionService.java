package net.infumia.frame.injector;

import net.infumia.frame.service.Service;

public interface InjectionService<C> extends Service<InjectionRequest<C>, Object> {
    static <C> InjectionService<C> create(final InjectorRegistry<C> registry) {
        return new InjectionServiceImpl<>(registry);
    }
}
