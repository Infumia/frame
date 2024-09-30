package net.infumia.inv.api.util;

import java.lang.reflect.Field;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Reflection {

    public static boolean hasClass(@NotNull final String className) {
        return Reflection.findClass(className) != null;
    }

    @Nullable
    public static Class<?> findClass(@NotNull final String className) {
        try {
            return Class.forName(className);
        } catch (final ClassNotFoundException e) {
            return null;
        }
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public static <T> T findInstanceFromField(
        @NotNull final String className,
        @NotNull final String fieldName
    ) {
        final Class<?> cls = Preconditions.argumentNotNull(
            Reflection.findClass(className),
            "Class '%s' not found",
            className
        );
        Field field = null;
        boolean old = false;
        try {
            field = cls.getDeclaredField(fieldName);
            old = field.isAccessible();
            field.setAccessible(true);
            return Preconditions.argumentNotNull(
                (T) field.get(null),
                "Field '%s' value is null in class '%s'!",
                fieldName,
                className
            );
        } catch (final NoSuchFieldException e) {
            throw new RuntimeException(
                String.format("Field '%s' not found in class '%s'!", fieldName, className),
                e
            );
        } catch (final IllegalAccessException e) {
            throw new RuntimeException(
                String.format("Cannot access to field '%s' in class '%s'!", fieldName, className),
                e
            );
        } finally {
            if (field != null) {
                field.setAccessible(old);
            }
        }
    }

    private Reflection() {
        throw new IllegalStateException("Utility class!");
    }
}
