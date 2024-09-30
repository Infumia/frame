package net.infumia.inv.api.typedkey;

import org.jetbrains.annotations.NotNull;

public interface TypedKeyStorageImmutableBuilder {
    @NotNull
    <T> TypedKeyStorageImmutableBuilder add(@NotNull TypedKey<T> key, @NotNull T value);

    @NotNull
    <T> TypedKeyStorageImmutableBuilder remove(@NotNull TypedKey<T> key);

    @NotNull
    TypedKeyStorageImmutable build();
}
