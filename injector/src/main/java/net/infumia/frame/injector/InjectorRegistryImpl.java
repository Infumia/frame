package net.infumia.frame.injector;

import io.leangen.geantyref.GenericTypeReflector;
import io.leangen.geantyref.TypeToken;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.infumia.frame.Pair;
import net.infumia.frame.Preconditions;

final class InjectorRegistryImpl<C> implements InjectorRegistry<C> {

    private final List<Pair<Predicate<TypeToken<?>>, Injector<C>>> providers = new ArrayList<>();

    InjectorRegistryImpl() {}

    @Override
    public InjectorRegistry<C> register(final Injector<C> injector) {
        Preconditions.argumentNotNull(injector, "injector");

        return this.register(__ -> true, injector);
    }

    @Override
    public InjectorRegistry<C> unregister(final Injector<C> injector) {
        Preconditions.argumentNotNull(injector, "injector");

        this.providers.removeIf(pair -> pair.second().equals(injector));
        return this;
    }

    @Override
    public synchronized InjectorRegistry<C> register(
        final Class<?> cls,
        final Injector<C> injector
    ) {
        Preconditions.argumentNotNull(cls, "cls");
        Preconditions.argumentNotNull(injector, "injector");

        return this.register(TypeToken.get(cls), injector);
    }

    @Override
    public synchronized InjectorRegistry<C> register(
        final TypeToken<?> type,
        final Injector<C> injector
    ) {
        Preconditions.argumentNotNull(type, "type");
        Preconditions.argumentNotNull(injector, "injector");

        return this.register(
                cl -> GenericTypeReflector.isSuperType(cl.getType(), type.getType()),
                injector
            );
    }

    @Override
    public synchronized InjectorRegistry<C> register(
        final Predicate<TypeToken<?>> predicate,
        final Injector<C> injector
    ) {
        Preconditions.argumentNotNull(predicate, "predicate");
        Preconditions.argumentNotNull(injector, "injector");

        this.providers.add(Pair.of(predicate, injector));
        return this;
    }

    @Override
    public synchronized <T> Collection<Injector<C>> injectors(final TypeToken<T> type) {
        Preconditions.argumentNotNull(type, "type");

        return Collections.unmodifiableCollection(
            this.providers.stream()
                .filter(pair -> pair.first().test(type))
                .map(Pair::second)
                .collect(Collectors.toList())
        );
    }
}
