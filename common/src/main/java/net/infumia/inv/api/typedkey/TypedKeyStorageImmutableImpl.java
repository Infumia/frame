package net.infumia.inv.api.typedkey;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class TypedKeyStorageImmutableImpl implements TypedKeyStorageImmutable {

    @NotNull
    protected final Map<TypedKey<?>, Object> map;

    TypedKeyStorageImmutableImpl(@NotNull final Map<TypedKey<?>, Object> map) {
        this.map = map;
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(@NotNull final TypedKey<T> key) {
        return (T) this.map.get(key);
    }

    @Nullable
    @Override
    public Object get(@NotNull final String key) {
        return this.map.keySet()
            .stream()
            .filter(k -> k.key().equals(key))
            .findFirst()
            .map(this::get)
            .orElse(null);
    }

    @Override
    public boolean contains(@NotNull final TypedKey<?> key) {
        return this.map.containsKey(key);
    }

    @NotNull
    @Override
    public Collection<TypedKey<?>> keys() {
        return Collections.unmodifiableCollection(this.map.keySet());
    }

    @NotNull
    @Override
    public Collection<Object> values() {
        return Collections.unmodifiableCollection(this.map.values());
    }

    @NotNull
    @Override
    public Collection<Map.Entry<TypedKey<?>, Object>> entries() {
        return Collections.unmodifiableCollection(this.map.entrySet());
    }

    @Override
    public String toString() {
        return "TypedKeyStorageImmutableImpl{" + "map=" + this.map + '}';
    }
}
