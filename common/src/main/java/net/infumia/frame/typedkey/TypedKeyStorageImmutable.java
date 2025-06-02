package net.infumia.frame.typedkey;

import java.util.Collection;
import java.util.Map;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

/**
 * A typed key storage immutable is a storage that is used to store values by typed keys.
 */
public interface TypedKeyStorageImmutable {
    /**
     * Gets a value from the storage.
     *
     * @param key the key
     * @return the value
     */
    @Nullable
    <T> T get(@NotNull TypedKey<T> key);

    /**
     * Gets a value from the storage.
     *
     * @param key the key
     * @return the value
     */
    @Nullable
    Object get(@NotNull String key);

    /**
     * Gets a value from the storage.
     *
     * @param key the key
     * @return the value
     */
    @Nullable
    <T> T getUnchecked(@NotNull String key);

    /**
     * Gets a value from the storage.
     *
     * @param key the key
     * @return the value
     */
    @NotNull
    <T> T getOrThrow(@NotNull TypedKey<T> key);

    @Contract("_, null -> null; _, !null -> !null")
    <T> T getOrDefault(@NotNull TypedKey<T> key, @Nullable T defaultValue);

    /**
     * Gets a value from the storage.
     *
     * @param key the key
     * @return the value
     */
    @NotNull
    Object getOrThrow(@NotNull String key);

    @Contract("_, null -> null; _, !null -> !null")
    Object getOrDefault(@NotNull String key, @Nullable Object defaultValue);

    /**
     * Gets a value from the storage.
     *
     * @param key the key
     * @return the value
     */
    @NotNull
    <T> T getUncheckedOrThrow(@NotNull String key);

    /**
     * Checks if the storage contains a key.
     *
     * @param key the key
     * @return true if the storage contains a key, false otherwise
     */
    boolean contains(@NotNull TypedKey<?> key);

    /**
     * Gets the keys from the storage.
     *
     * @return the keys
     */
    @NotNull
    @UnmodifiableView
    Collection<TypedKey<?>> keys();

    /**
     * Gets the values from the storage.
     *
     * @return the values
     */
    @NotNull
    @UnmodifiableView
    Collection<Object> values();

    /**
     * Gets the entries from the storage.
     *
     * @return the entries
     */
    @NotNull
    @UnmodifiableView
    Collection<Map.Entry<TypedKey<?>, Object>> entries();

    /**
     * Gets the map from the storage.
     *
     * @return the map
     */
    @NotNull
    @UnmodifiableView
    Map<TypedKey<?>, Object> map();
}
