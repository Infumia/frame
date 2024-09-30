package net.infumia.inv.api.typedkey;

import java.util.Collections;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

final class TypedKeyStorageImmutableBuilderImpl implements TypedKeyStorageImmutableBuilder {

    @NotNull
    private final Map<TypedKey<?>, Object> map;

    TypedKeyStorageImmutableBuilderImpl(@NotNull final Map<TypedKey<?>, Object> map) {
        this.map = map;
    }

    @NotNull
    @Override
    public <T> TypedKeyStorageImmutableBuilder add(
        @NotNull final TypedKey<T> key,
        @NotNull final T value
    ) {
        this.map.put(key, value);
        return this;
    }

    @NotNull
    @Override
    public <T> TypedKeyStorageImmutableBuilder remove(@NotNull final TypedKey<T> key) {
        this.map.remove(key);
        return this;
    }

    @NotNull
    @Override
    public TypedKeyStorageImmutable build() {
        return new TypedKeyStorageImmutableImpl(Collections.unmodifiableMap(this.map));
    }
}
