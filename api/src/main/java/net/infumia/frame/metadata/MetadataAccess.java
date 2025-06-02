package net.infumia.frame.metadata;

import java.util.concurrent.Callable;
import net.infumia.frame.typedkey.TypedKey;
import org.bukkit.metadata.LazyMetadataValue;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface MetadataAccess {
    @Nullable
    <T> T get(@NotNull TypedKey<T> key);

    @NotNull
    <T> T getOrThrow(@NotNull TypedKey<T> key);

    @Contract("_, null -> null; _, !null -> !null")
    <T> T getOrDefault(@NotNull TypedKey<T> key, @Nullable T defaultValue);

    @Nullable
    <T> T remove(@NotNull TypedKey<T> key);

    boolean has(@NotNull TypedKey<?> key);

    <T> void setFixed(@NotNull TypedKey<T> key, @NotNull T value);

    <T> void setLazy(
        @NotNull TypedKey<T> key,
        @NotNull Callable<T> value,
        @NotNull LazyMetadataValue.CacheStrategy cacheStrategy
    );

    <T> void setLazy(@NotNull TypedKey<T> key, @NotNull Callable<T> value);

    void removeAll();
}
