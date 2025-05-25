package net.infumia.frame.injector.guice;

import com.google.inject.Key;
import net.infumia.frame.injector.AnnotationAccessor;

@FunctionalInterface
public interface KeyCreator {
    static KeyCreator noBinding() {
        return KeyCreatorNoBinding.INSTANCE;
    }

    static KeyCreator firstBinding() {
        return KeyCreatorFirstBinding.INSTANCE;
    }

    <T> Key<T> create(Class<T> cls, AnnotationAccessor accessor);
}
