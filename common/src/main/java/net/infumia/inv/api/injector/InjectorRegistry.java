package net.infumia.inv.api.injector;

import io.leangen.geantyref.TypeToken;
import java.util.Collection;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;

public interface InjectorRegistry<C> {
    @NotNull
    static <C> InjectorRegistry<C> create() {
        return new InjectorRegistryImpl<>();
    }

    @NotNull
    InjectorRegistry<C> register(@NotNull Injector<C> injector);

    @NotNull
    InjectorRegistry<C> register(@NotNull Class<?> cls, @NotNull Injector<C> injector);

    @NotNull
    InjectorRegistry<C> register(@NotNull TypeToken<?> type, @NotNull Injector<C> injector);

    @NotNull
    InjectorRegistry<C> register(
        @NotNull Predicate<TypeToken<?>> predicate,
        @NotNull Injector<C> injector
    );

    @NotNull
    <T> Collection<Injector<C>> injectors(@NotNull TypeToken<T> type);
}
