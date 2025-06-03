package net.infumia.frame.injector;

import java.lang.reflect.Field;
import net.infumia.frame.Preconditions;

public final class InjectorStaticInstance implements Injector<Object> {

    public static final Injector<Object> INSTANCE = new InjectorStaticInstance();

    private InjectorStaticInstance() {}

    @Override
    public Object inject(final InjectionRequest<Object> ctx) {
        Preconditions.argumentNotNull(ctx, "ctx");

        final Class<?> viewClass = ctx.injectedClass();
        Field field = null;
        boolean old = false;
        try {
            field = viewClass.getDeclaredField("INSTANCE");
            field.setAccessible(true);
            old = field.isAccessible();
            return field.get(null);
        } catch (final Exception ignored) {} finally {
            if (field != null) {
                field.setAccessible(old);
            }
        }
        return null;
    }
}
