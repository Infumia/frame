package net.infumia.frame.view;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.injector.InjectionServicePipeline;
import net.infumia.frame.injector.InjectorRegistry;
import net.infumia.frame.pipeline.Pipelined;
import org.jetbrains.annotations.NotNull;

public interface ViewFactory extends Pipelined<InjectionServicePipeline<Object>> {
    @NotNull
    InjectorRegistry<Object> injectors();

    @NotNull
    CompletableFuture<Object> create(@NotNull Class<?> viewClass);
}
