package net.infumia.frame.injector;

import io.leangen.geantyref.TypeToken;
import java.util.Collection;
import java.util.function.Predicate;

public interface InjectorRegistry<C> {
    static <C> InjectorRegistry<C> create() {
        return new InjectorRegistryImpl<>();
    }

    InjectorRegistry<C> register(Injector<C> injector);

    InjectorRegistry<C> unregister(Injector<C> injector);

    InjectorRegistry<C> register(Class<?> cls, Injector<C> injector);

    InjectorRegistry<C> register(TypeToken<?> type, Injector<C> injector);

    InjectorRegistry<C> register(Predicate<TypeToken<?>> predicate, Injector<C> injector);

    <T> Collection<Injector<C>> injectors(TypeToken<T> type);
}
