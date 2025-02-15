package net.infumia.frame.view;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.injection.InjectionServicePipeline;
import net.infumia.frame.injector.InjectorRegistry;
import net.infumia.frame.pipeline.Pipelined;
import org.jetbrains.annotations.NotNull;

public interface ViewCreator extends Pipelined<InjectionServicePipeline<Object>> {
    @NotNull
    InjectorRegistry<Object> injectors();

    @NotNull
    CompletableFuture<Object> create(@NotNull Class<?> viewClass);
}
