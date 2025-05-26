package net.infumia.frame.injector.guice;

import com.google.inject.Key;
import net.infumia.frame.Preconditions;
import net.infumia.frame.injector.AnnotationAccessor;

final class KeyCreatorNoBinding implements KeyCreator {

    static final KeyCreator INSTANCE = new KeyCreatorNoBinding();

    private KeyCreatorNoBinding() {}

    @Override
    public <T> Key<T> create(final Class<T> cls, final AnnotationAccessor accessor) {
        Preconditions.argumentNotNull(cls, "cls");
        Preconditions.argumentNotNull(accessor, "accessor");

        return Key.get(cls);
    }
}
