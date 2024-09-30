package net.infumia.inv.api.typedkey;

import java.util.function.Function;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface TypedKeyStorage extends TypedKeyStorageImmutable {
    <T> boolean put(@NotNull TypedKey<T> key, @NotNull T value);

    @NotNull
    <T> T compute(@NotNull TypedKey<T> key, @NotNull Function<@Nullable T, T> oldValueToNewValue);

    @NotNull
    <T> T computeIfAbsent(@NotNull TypedKey<T> key, @NotNull Supplier<T> valueSupplier);

    @Nullable
    <T> T computeIfPresent(@NotNull TypedKey<T> key, @NotNull Function<T, T> oldValueToNewValue);

    <T> boolean remove(@NotNull TypedKey<T> key);
}
