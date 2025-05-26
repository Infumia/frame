package net.infumia.frame.injector;

import java.lang.reflect.Constructor;
import net.infumia.frame.Preconditions;

public final class InjectorNoArg implements Injector<Object> {

    public static final Injector<Object> INSTANCE = new InjectorNoArg();

    private InjectorNoArg() {}

    @Override
    public Object inject(final InjectionRequest<Object> ctx) {
        Preconditions.argumentNotNull(ctx, "ctx");

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
