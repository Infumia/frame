package net.infumia.frame.typedkey;

import io.leangen.geantyref.TypeToken;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public final class TypedKey<T> {

    private final TypeToken<T> cls;
    private final String key;

    private TypedKey(@NotNull final TypeToken<T> cls, @NotNull final String key) {
        this.cls = cls;
        this.key = key;
    }

    @NotNull
    public static <T> TypedKey<T> of(@NotNull final TypeToken<T> type, @NotNull final String key) {
        return new TypedKey<>(type, key);
    }

    @NotNull
    public static <T> TypedKey<T> of(@NotNull final Class<T> cls, @NotNull final String key) {
        return TypedKey.of(TypeToken.get(cls), key);
    }

    @NotNull
    public TypeToken<T> cls() {
        return this.cls;
    }

    @NotNull
    public String key() {
        return this.key;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.key);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final TypedKey<?> key1 = (TypedKey<?>) o;
        return Objects.equals(this.key, key1.key);
    }

    @Override
    public String toString() {
        return "TypedKey{" + "cls=" + this.cls + ", key='" + this.key + '\'' + '}';
    }
}
