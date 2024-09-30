package net.infumia.inv.api.view;

import java.util.concurrent.CompletableFuture;
import net.infumia.inv.api.injection.InjectionServicePipeline;
import net.infumia.inv.api.injector.InjectorRegistry;
import org.jetbrains.annotations.NotNull;

public interface ViewCreator {
    @NotNull
    InjectionServicePipeline<Object> pipeline();

    @NotNull
    InjectorRegistry<Object> injectors();

    @NotNull
    CompletableFuture<Object> create(@NotNull Class<?> viewClass);
}
