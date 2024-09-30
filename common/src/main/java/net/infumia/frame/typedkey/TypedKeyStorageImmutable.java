package net.infumia.frame.typedkey;

import java.util.Collection;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

public interface TypedKeyStorageImmutable {
    @Nullable
    <T> T get(@NotNull TypedKey<T> key);

    @Nullable
    Object get(@NotNull String key);

    @Nullable
    <T> T getUnchecked(@NotNull String key);

    @NotNull
    <T> T getOrThrow(@NotNull TypedKey<T> key);

    @NotNull
    Object getOrThrow(@NotNull String key);

    @NotNull
    <T> T getUncheckedOrThrow(@NotNull String key);

    boolean contains(@NotNull TypedKey<?> key);

    @NotNull
    @UnmodifiableView
    Collection<TypedKey<?>> keys();

    @NotNull
    @UnmodifiableView
    Collection<Object> values();

    @NotNull
    @UnmodifiableView
    Collection<Map.Entry<TypedKey<?>, Object>> entries();
}
