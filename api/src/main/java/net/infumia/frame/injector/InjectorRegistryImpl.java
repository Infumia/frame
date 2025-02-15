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
import org.jetbrains.annotations.NotNull;

final class InjectorRegistryImpl<C> implements InjectorRegistry<C> {

    private final List<Pair<Predicate<TypeToken<?>>, Injector<C>>> providers = new ArrayList<>();

    InjectorRegistryImpl() {}

    @NotNull
    @Override
    public InjectorRegistry<C> register(@NotNull final Injector<C> injector) {
        return this.register(__ -> true, injector);
    }

    @NotNull
    @Override
    public synchronized InjectorRegistry<C> register(
        @NotNull final Class<?> cls,
        @NotNull final Injector<C> injector
    ) {
        return this.register(TypeToken.get(cls), injector);
    }

    @NotNull
    @Override
    public synchronized InjectorRegistry<C> register(
        @NotNull final TypeToken<?> type,
        @NotNull final Injector<C> injector
    ) {
        return this.register(
                cl -> GenericTypeReflector.isSuperType(cl.getType(), type.getType()),
                injector
            );
    }

    @NotNull
    @Override
    public synchronized InjectorRegistry<C> register(
        @NotNull final Predicate<TypeToken<?>> predicate,
        @NotNull final Injector<C> injector
    ) {
        this.providers.add(Pair.of(predicate, injector));
        return this;
    }

    @NotNull
    @Override
    public synchronized <T> Collection<Injector<C>> injectors(@NotNull final TypeToken<T> type) {
        return Collections.unmodifiableCollection(
            this.providers.stream()
                .filter(pair -> pair.first().test(type))
                .map(Pair::second)
                .collect(Collectors.toList())
        );
    }
}
