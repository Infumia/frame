package net.infumia.frame.injector.guice;

import com.google.inject.Key;
import net.infumia.frame.injection.AnnotationAccessor;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface KeyCreator {
    @NotNull
    static KeyCreator noBinding() {
        return KeyCreatorNoBinding.INSTANCE;
    }

    @NotNull
    static KeyCreator firstBinding() {
        return KeyCreatorFirstBinding.INSTANCE;
    }

    @NotNull
    <T> Key<T> create(@NotNull Class<T> cls, @NotNull AnnotationAccessor accessor);
}
