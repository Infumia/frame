package net.infumia.frame.injector.guice;

import com.google.inject.ConfigurationException;
import java.util.Objects;
import net.infumia.frame.injector.InjectionRequest;
import net.infumia.frame.injector.Injector;

public final class InjectorGuice<C> implements Injector<C> {

    private final com.google.inject.Injector injector;
    private final KeyCreator keyCreator;

    public InjectorGuice(final com.google.inject.Injector injector, final KeyCreator keyCreator) {
        this.injector = Objects.requireNonNull(injector, "injector");
        this.keyCreator = Objects.requireNonNull(keyCreator, "keyCreator");
    }

    public InjectorGuice(final com.google.inject.Injector injector) {
        this(injector, KeyCreator.firstBinding());
    }

    @Override
    public Object inject(final InjectionRequest<C> ctx) {
        Objects.requireNonNull(ctx, "ctx");

        try {
            return this.injector.getInstance(
                    this.keyCreator.create(ctx.injectedClass(), ctx.annotationAccessor())
                );
        } catch (final ConfigurationException ignored) {
            return null;
        }
    }
}
