package net.infumia.frame.view;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.injection.InjectionServicePipeline;
import net.infumia.frame.injector.InjectorRegistry;
import org.jetbrains.annotations.NotNull;

public interface ViewCreator {
    @NotNull
    InjectionServicePipeline<Object> pipeline();

    @NotNull
    InjectorRegistry<Object> injectors();

    @NotNull
    CompletableFuture<Object> create(@NotNull Class<?> viewClass);
}
