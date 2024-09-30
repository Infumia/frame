package net.infumia.frame.typedkey;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

final class TypedKeyStorageImmutableEmpty implements TypedKeyStorageImmutable {

    static final TypedKeyStorageImmutable INSTANCE = new TypedKeyStorageImmutableEmpty();

    private TypedKeyStorageImmutableEmpty() {}

    @Nullable
    @Override
    public <T> T get(@NotNull final TypedKey<T> key) {
        return null;
    }

    @Nullable
    @Override
    public Object get(@NotNull final String key) {
        return null;
    }

    @Override
    public boolean contains(@NotNull final TypedKey<?> key) {
        return false;
    }

    @NotNull
    @Override
    @UnmodifiableView
    public Collection<TypedKey<?>> keys() {
        return Collections.emptySet();
    }

    @NotNull
    @Override
    @UnmodifiableView
    public Collection<Object> values() {
        return Collections.emptySet();
    }

    @NotNull
    @Override
    @UnmodifiableView
    public Collection<Map.Entry<TypedKey<?>, Object>> entries() {
        return Collections.emptySet();
    }

    @Override
    public String toString() {
        return "TypedKeyStorageImmutableEmpty{}";
    }
}
