package net.infumia.frame.typedkey;

import java.util.Map;
import org.jetbrains.annotations.NotNull;

public interface TypedKeyStorageFactory {
    @NotNull
    static TypedKeyStorageFactory create() {
        return new TypedKeyStorageFactoryImpl();
    }

    @NotNull
    TypedKeyStorage create(@NotNull Map<TypedKey<?>, Object> base);

    @NotNull
    TypedKeyStorageImmutableBuilder createImmutableBuilder(@NotNull Map<TypedKey<?>, Object> base);
}
