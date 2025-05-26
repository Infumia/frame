package net.infumia.frame.typedkey;

import java.util.function.Function;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A typed key storage is a storage that is used to store values by typed keys.
 */
public interface TypedKeyStorage extends TypedKeyStorageImmutable {
    /**
     * Puts a value into the storage.
     *
     * @param key the key
     * @param value the value
     * @return true if the value was put, false otherwise
     */
    <T> boolean put(@NotNull TypedKey<T> key, @NotNull T value);

    /**
     * Computes a new value for the given key.
     *
     * @param key the key
     * @param oldValueToNewValue the function to compute the new value
     * @return the new value
     */
    @NotNull
    <T> T compute(@NotNull TypedKey<T> key, @NotNull Function<@Nullable T, T> oldValueToNewValue);

    /**
     * Computes a new value for the given key if the key is absent.
     *
     * @param key the key
     * @param valueSupplier the supplier to compute the new value
     * @return the new value
     */
    @NotNull
    <T> T computeIfAbsent(@NotNull TypedKey<T> key, @NotNull Supplier<T> valueSupplier);

    /**
     * Computes a new value for the given key if the key is present.
     *
     * @param key the key
     * @param oldValueToNewValue the function to compute the new value
     * @return the new value
     */
    @Nullable
    <T> T computeIfPresent(@NotNull TypedKey<T> key, @NotNull Function<T, T> oldValueToNewValue);

    /**
     * Removes a value from the storage.
     *
     * @param key the key
     * @return true if the value was removed, false otherwise
     */
    <T> boolean remove(@NotNull TypedKey<T> key);
}
