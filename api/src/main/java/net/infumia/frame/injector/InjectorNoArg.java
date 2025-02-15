package net.infumia.frame.injector;

import java.lang.reflect.Constructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class InjectorNoArg implements Injector<Object> {

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
