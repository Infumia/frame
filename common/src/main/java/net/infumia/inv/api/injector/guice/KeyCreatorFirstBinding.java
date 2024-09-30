package net.infumia.inv.api.injector.guice;

import com.google.inject.BindingAnnotation;
import com.google.inject.Key;
import net.infumia.inv.api.injection.AnnotationAccessor;
import org.jetbrains.annotations.NotNull;

final class KeyCreatorFirstBinding implements KeyCreator {

    static final KeyCreator INSTANCE = new KeyCreatorFirstBinding();

    private KeyCreatorFirstBinding() {}

    @NotNull
    @Override
    public <T> Key<T> create(
        @NotNull final Class<T> cls,
        @NotNull final AnnotationAccessor accessor
    ) {
        return accessor
            .annotations()
            .stream()
            .filter(a -> a.annotationType().isAnnotationPresent(BindingAnnotation.class))
            .findFirst()
            .map(a -> Key.get(cls, a))
            .orElseGet(() -> Key.get(cls));
    }
}
