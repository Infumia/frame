package net.infumia.frame.injector;

import io.leangen.geantyref.GenericTypeReflector;
import io.leangen.geantyref.TypeToken;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.infumia.frame.Pair;

final class InjectorRegistryImpl<C> implements InjectorRegistry<C> {

    private final List<Pair<Predicate<TypeToken<?>>, Injector<C>>> providers = new ArrayList<>();

    InjectorRegistryImpl() {}

    @Override
    public InjectorRegistry<C> register(final Injector<C> injector) {
        Objects.requireNonNull(injector, "injector");

        return this.register(__ -> true, injector);
    }

    @Override
    public synchronized InjectorRegistry<C> register(
        final Class<?> cls,
        final Injector<C> injector
    ) {
        Objects.requireNonNull(cls, "cls");
        Objects.requireNonNull(injector, "injector");

        return this.register(TypeToken.get(cls), injector);
    }

    @Override
    public synchronized InjectorRegistry<C> register(
        final TypeToken<?> type,
        final Injector<C> injector
    ) {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(injector, "injector");

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
        Objects.requireNonNull(predicate, "predicate");
        Objects.requireNonNull(injector, "injector");

        this.providers.add(Pair.of(predicate, injector));
        return this;
    }

    @Override
    public synchronized <T> Collection<Injector<C>> injectors(final TypeToken<T> type) {
        Objects.requireNonNull(type, "type");

        return Collections.unmodifiableCollection(
            this.providers.stream()
                .filter(pair -> pair.first().test(type))
                .map(Pair::second)
                .collect(Collectors.toList())
        );
    }
}
