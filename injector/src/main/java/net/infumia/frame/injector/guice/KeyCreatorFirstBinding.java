package net.infumia.frame.injector.guice;

import com.google.inject.BindingAnnotation;
import com.google.inject.Key;
import java.util.Objects;
import net.infumia.frame.injector.AnnotationAccessor;

final class KeyCreatorFirstBinding implements KeyCreator {

    static final KeyCreator INSTANCE = new KeyCreatorFirstBinding();

    private KeyCreatorFirstBinding() {}

    @Override
    public <T> Key<T> create(final Class<T> cls, final AnnotationAccessor accessor) {
        Objects.requireNonNull(cls, "cls");
        Objects.requireNonNull(accessor, "accessor");

        return accessor
            .annotations()
            .stream()
            .filter(a -> a.annotationType().isAnnotationPresent(BindingAnnotation.class))
            .findFirst()
            .map(a -> Key.get(cls, a))
            .orElseGet(() -> Key.get(cls));
    }
}
