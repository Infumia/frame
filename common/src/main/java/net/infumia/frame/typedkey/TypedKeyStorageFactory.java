package net.infumia.frame.typedkey;

import java.util.Map;
import org.jetbrains.annotations.NotNull;

/**
 * A typed key storage factory is a factory that is used to create typed key storages.
 */
public interface TypedKeyStorageFactory {
    /**
     * Creates a new typed key storage factory.
     *
     * @return the new typed key storage factory
     */
    @NotNull
    static TypedKeyStorageFactory create() {
        return new TypedKeyStorageFactoryImpl();
    }

    /**
     * Creates a new typed key storage.
     *
     * @param base the base map
     * @return the new typed key storage
     */
    @NotNull
    TypedKeyStorage create(@NotNull Map<TypedKey<?>, Object> base);

    /**
     * Creates a new typed key storage immutable builder.
     *
     * @param base the base map
     * @return the new typed key storage immutable builder
     */
    @NotNull
    TypedKeyStorageImmutableBuilder createImmutableBuilder(@NotNull Map<TypedKey<?>, Object> base);
}
