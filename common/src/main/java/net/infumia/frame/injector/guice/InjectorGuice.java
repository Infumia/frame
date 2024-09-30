package net.infumia.frame.injector.guice;

import com.google.inject.ConfigurationException;
import net.infumia.frame.injector.InjectionRequest;
import net.infumia.frame.injector.Injector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class InjectorGuice<C> implements Injector<C> {

    @NotNull
    private final com.google.inject.Injector injector;

    @NotNull
    private final KeyCreator keyCreator;

    public InjectorGuice(
        @NotNull final com.google.inject.Injector injector,
        @NotNull final KeyCreator keyCreator
    ) {
        this.injector = injector;
        this.keyCreator = keyCreator;
    }

    public InjectorGuice(@NotNull final com.google.inject.Injector injector) {
        this(injector, KeyCreator.firstBinding());
    }

    @Nullable
    @Override
    public Object inject(@NotNull final InjectionRequest<C> ctx) {
        try {
            return this.injector.getInstance(
                    this.keyCreator.create(ctx.injectedClass(), ctx.annotationAccessor())
                );
        } catch (final ConfigurationException ignored) {
            return null;
        }
    }
}
