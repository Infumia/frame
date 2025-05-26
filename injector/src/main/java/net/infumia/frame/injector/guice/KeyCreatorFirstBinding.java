package net.infumia.frame.injector.guice;

import com.google.inject.BindingAnnotation;
import com.google.inject.Key;
import net.infumia.frame.Preconditions;
import net.infumia.frame.injector.AnnotationAccessor;

final class KeyCreatorFirstBinding implements KeyCreator {

    static final KeyCreator INSTANCE = new KeyCreatorFirstBinding();

    private KeyCreatorFirstBinding() {}

    @Override
    public <T> Key<T> create(final Class<T> cls, final AnnotationAccessor accessor) {
        Preconditions.argumentNotNull(cls, "cls");
        Preconditions.argumentNotNull(accessor, "accessor");

        return accessor
            .annotations()
            .stream()
            .filter(a -> a.annotationType().isAnnotationPresent(BindingAnnotation.class))
            .findFirst()
            .map(a -> Key.get(cls, a))
            .orElseGet(() -> Key.get(cls));
    }
}
