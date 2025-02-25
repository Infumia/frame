package net.infumia.frame.injector;

import java.lang.reflect.Constructor;
import java.util.Objects;

public final class InjectorNoArg implements Injector<Object> {

    @Override
    public Object inject(final InjectionRequest<Object> ctx) {
        Objects.requireNonNull(ctx, "ctx");

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
