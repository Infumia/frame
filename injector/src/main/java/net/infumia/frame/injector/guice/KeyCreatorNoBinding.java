package net.infumia.frame.injector.guice;

import com.google.inject.Key;
import java.util.Objects;
import net.infumia.frame.injector.AnnotationAccessor;

final class KeyCreatorNoBinding implements KeyCreator {

    static final KeyCreator INSTANCE = new KeyCreatorNoBinding();

    private KeyCreatorNoBinding() {}

    @Override
    public <T> Key<T> create(final Class<T> cls, final AnnotationAccessor accessor) {
        Objects.requireNonNull(cls, "cls");
        Objects.requireNonNull(accessor, "accessor");

        return Key.get(cls);
    }
}
