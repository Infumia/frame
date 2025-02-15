package net.infumia.frame.injector.guice;

import com.google.inject.Key;
import net.infumia.frame.injection.AnnotationAccessor;
import org.jetbrains.annotations.NotNull;

final class KeyCreatorNoBinding implements KeyCreator {

    static final KeyCreator INSTANCE = new KeyCreatorNoBinding();

    private KeyCreatorNoBinding() {}

    @NotNull
    @Override
    public <T> Key<T> create(
        @NotNull final Class<T> cls,
        @NotNull final AnnotationAccessor accessor
    ) {
        return Key.get(cls);
    }
}
