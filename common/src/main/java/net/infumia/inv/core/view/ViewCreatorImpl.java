package net.infumia.inv.core.view;

import java.lang.reflect.Constructor;
import java.util.concurrent.CompletableFuture;
import net.infumia.inv.api.injection.AnnotationAccessor;
import net.infumia.inv.api.injection.InjectionRequester;
import net.infumia.inv.api.injection.InjectionService;
import net.infumia.inv.api.injection.InjectionServicePipeline;
import net.infumia.inv.api.injector.InjectionRequest;
import net.infumia.inv.api.injector.Injector;
import net.infumia.inv.api.injector.InjectorRegistry;
import net.infumia.inv.api.util.Preconditions;
import net.infumia.inv.api.view.ViewCreator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ViewCreatorImpl implements ViewCreator {

    private final InjectorRegistry<Object> injectors;
    private final InjectionServicePipeline<Object> pipeline;
    private final InjectionRequester<Object> requester;

    public ViewCreatorImpl() {
        this.injectors = InjectorRegistry.create().register(new NoArg());
        this.pipeline = InjectionServicePipeline.create(InjectionService.create(this.injectors));
        this.requester = InjectionRequester.create(this.pipeline);
    }

    @NotNull
    @Override
    public InjectionServicePipeline<Object> pipeline() {
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

    private static final class NoArg implements Injector<Object> {

        @Nullable
        @Override
        public Object inject(@NotNull final InjectionRequest<Object> ctx) {
            final Class<?> viewClass = ctx.injectedClass();
            Constructor<?> constructor = null;
            boolean old = false;
            try {
                constructor = viewClass.getConstructor();
                old = constructor.isAccessible();
                return constructor.newInstance();
            } catch (final Exception ignored) {} finally {
                if (constructor != null) {
                    constructor.setAccessible(old);
                }
            }
            return null;
        }
    }
}
