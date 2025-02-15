package net.infumia.frame.view;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.Preconditions;
import net.infumia.frame.injector.AnnotationAccessor;
import net.infumia.frame.injector.InjectionRequester;
import net.infumia.frame.injector.InjectionService;
import net.infumia.frame.injector.InjectionServicePipeline;
import net.infumia.frame.injector.InjectorNoArg;
import net.infumia.frame.injector.InjectorRegistry;
import org.jetbrains.annotations.NotNull;

public final class ViewCreatorImpl implements ViewCreator {

    private final InjectorRegistry<Object> injectors;
    private final InjectionServicePipeline<Object> pipeline;
    private final InjectionRequester<Object> requester;

    public ViewCreatorImpl() {
        this.injectors = InjectorRegistry.create().register(new InjectorNoArg());
        this.pipeline = InjectionServicePipeline.create(InjectionService.create(this.injectors));
        this.requester = InjectionRequester.create(this.pipeline);
    }

    @NotNull
    @Override
    public InjectionServicePipeline<Object> pipelines() {
        return this.pipeline;
    }

    @NotNull
    @Override
    public InjectorRegistry<Object> injectors() {
        return this.injectors;
    }

    @NotNull
    @Override
    public CompletableFuture<Object> create(@NotNull final Class<?> viewClass) {
        return this.requester.request(
                viewClass,
                viewClass,
                AnnotationAccessor.of(viewClass)
            ).handle((value, throwable) -> {
                if (throwable != null) {
                    throw new IllegalArgumentException(
                        String.format("Could not create view instance of '%s'.", viewClass),
                        throwable
                    );
                }
                return Preconditions.argumentNotNull(
                    value,
                    "Both error and result is null when requesting an injection for view class '%s'. Please report this bug to the inventory framework you are using.",
                    viewClass
                );
            });
    }
}
