package net.infumia.frame.metadata;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import net.infumia.frame.Preconditions;
import net.infumia.frame.typedkey.TypedKey;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.LazyMetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class MetadataAccessImpl implements MetadataAccess {

    private final Collection<TypedKey<?>> registered = ConcurrentHashMap.newKeySet();
    private final Plugin plugin;
    private final Metadatable metadatable;

    public MetadataAccessImpl(
        @NotNull final Plugin plugin,
        @NotNull final Metadatable metadatable
    ) {
        this.plugin = plugin;
        this.metadatable = metadatable;
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(@NotNull final TypedKey<T> key) {
        return this.metadatable.getMetadata(key.key())
            .stream()
            .filter(metadataValue -> Objects.equals(metadataValue.getOwningPlugin(), this.plugin))
            .findFirst()
            .map(metadataValue -> (T) metadataValue.value())
            .orElse(null);
    }

    @NotNull
    @Override
    public <T> T getOrThrow(@NotNull final TypedKey<T> key) {
        return Preconditions.argumentNotNull(
            this.get(key),
            "Metadata value for key " + key + " not found!"
        );
    }

    @Override
    public <T> T getOrDefault(@NotNull final TypedKey<T> key, @Nullable final T defaultValue) {
        final T value = this.get(key);
        return value == null ? defaultValue : value;
    }

    @Nullable
    @Override
    public <T> T remove(@NotNull final TypedKey<T> key) {
        final T data = this.get(key);
        this.registered.remove(key);
        this.metadatable.removeMetadata(key.key(), this.plugin);
        return data;
    }

    @Override
    public boolean has(@NotNull final TypedKey<?> key) {
        return this.metadatable.hasMetadata(key.key());
    }

    @Override
    public <T> void setFixed(@NotNull final TypedKey<T> key, @NotNull final T value) {
        this.registered.add(key);
        this.metadatable.setMetadata(key.key(), new FixedMetadataValue(this.plugin, value));
    }

    @Override
    public <T> void setLazy(
        @NotNull final TypedKey<T> key,
        @NotNull final Callable<T> value,
        @NotNull final LazyMetadataValue.CacheStrategy cacheStrategy
    ) {
        this.registered.add(key);
        this.metadatable.setMetadata(
                key.key(),
                new LazyMetadataValue(this.plugin, cacheStrategy, value::call)
            );
    }

    @Override
    public <T> void setLazy(@NotNull final TypedKey<T> key, @NotNull final Callable<T> value) {
        this.setLazy(key, value, LazyMetadataValue.CacheStrategy.CACHE_AFTER_FIRST_EVAL);
    }

    @Override
    public void removeAll() {
        for (final TypedKey<?> key : this.registered) {
            this.metadatable.removeMetadata(key.key(), this.plugin);
        }
        this.registered.clear();
    }
}
