package net.infumia.frame.injector;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface Injector<C> {
    @Nullable
    Object inject(@NotNull InjectionRequest<C> ctx);
}
