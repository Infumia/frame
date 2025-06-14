package net.infumia.frame.typedkey;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class TypedKeyStorageImpl extends TypedKeyStorageImmutableImpl implements TypedKeyStorage {

    TypedKeyStorageImpl(@NotNull final Map<TypedKey<?>, Object> map) {
        super(map);
    }

    @Override
    public <T> boolean put(@NotNull final TypedKey<T> key, @NotNull final T value) {
        return this.map.put(key, value) == null;
    }

    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    public <T> T compute(
        @NotNull final TypedKey<T> key,
        @NotNull final Function<@Nullable T, T> oldValueToNewValue
    ) {
        return (T) this.map.compute(key, (__, oldValue) -> {
                if (oldValue == null) {
                    return oldValueToNewValue.apply(null);
                } else {
                    return oldValueToNewValue.apply((T) oldValue);
                }
            });
    }

    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    public <T> T computeIfAbsent(
        @NotNull final TypedKey<T> key,
        @NotNull final Supplier<T> valueSupplier
    ) {
        return (T) this.map.computeIfAbsent(key, __ -> valueSupplier.get());
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public <T> T computeIfPresent(
        @NotNull final TypedKey<T> key,
        @NotNull final Function<T, T> oldValueToNewValue
    ) {
        return (T) this.map.computeIfPresent(key, (__, oldValue) ->
                oldValueToNewValue.apply((T) oldValue)
            );
    }

    @Override
    public <T> boolean remove(@NotNull final TypedKey<T> key) {
        return this.map.remove(key) != null;
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    @Override
    public String toString() {
        return "TypedKeyStorageImpl{" + "map=" + this.map + '}';
    }
}
