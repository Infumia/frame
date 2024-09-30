package net.infumia.frame.typedkey;

import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

final class TypedKeyStorageFactoryImpl implements TypedKeyStorageFactory {

    TypedKeyStorageFactoryImpl() {}

    @NotNull
    @Override
    public TypedKeyStorage create(@NotNull final Map<TypedKey<?>, Object> base) {
        return new TypedKeyStorageImpl(base);
    }

    @NotNull
    @Override
    public TypedKeyStorageImmutableBuilder createImmutableBuilder() {
        return this.createImmutableBuilder(new HashMap<>());
    }

    @NotNull
    @Override
    public TypedKeyStorageImmutableBuilder createImmutableBuilder(
        @NotNull final Map<TypedKey<?>, Object> base
    ) {
        return new TypedKeyStorageImmutableBuilderImpl(base);
    }
}
