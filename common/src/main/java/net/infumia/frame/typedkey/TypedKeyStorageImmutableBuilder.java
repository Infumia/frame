package net.infumia.frame.typedkey;

import org.jetbrains.annotations.NotNull;

/**
 * A typed key storage immutable builder is a builder that is used to create typed key storages.
 */
public interface TypedKeyStorageImmutableBuilder {
    /**
     * Adds a value to the builder.
     *
     * @param key the key
     * @param value the value
     * @return the builder
     */
    @NotNull
    <T> TypedKeyStorageImmutableBuilder add(@NotNull TypedKey<T> key, @NotNull T value);

    /**
     * Removes a value from the builder.
     *
     * @param key the key
     * @return the builder
     */
    @NotNull
    <T> TypedKeyStorageImmutableBuilder remove(@NotNull TypedKey<T> key);

    /**
     * Builds the typed key storage immutable.
     *
     * @return the typed key storage immutable
     */
    @NotNull
    TypedKeyStorageImmutable build();
}
