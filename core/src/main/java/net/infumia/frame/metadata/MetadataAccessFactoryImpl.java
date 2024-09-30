package net.infumia.frame.metadata;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public final class MetadataAccessFactoryImpl implements MetadataAccessFactory {

    private final Map<String, MetadataAccess> cache = new ConcurrentHashMap<>();
    private final Plugin plugin;
    private final CacheKeyExtractor cacheKeyExtractor;

    public MetadataAccessFactoryImpl(
        @NotNull final Plugin plugin,
        @NotNull final CacheKeyExtractor cacheKeyExtractor
    ) {
        this.plugin = plugin;
        this.cacheKeyExtractor = cacheKeyExtractor;
    }

    public MetadataAccessFactoryImpl(@NotNull final Plugin plugin) {
        this(plugin, new CacheKeyExtractorEntityUniqueId());
    }

    @NotNull
    @Override
    public MetadataAccess getOrCreate(@NotNull final Metadatable metadatable) {
        return this.cache.computeIfAbsent(this.cacheKeyExtractor.apply(metadatable), __ ->
                new MetadataAccessImpl(this.plugin, metadatable)
            );
    }

    @Override
    public void clearCache(@NotNull final Collection<? extends Metadatable> metadatables) {
        metadatables
            .stream()
            .map(this.cacheKeyExtractor)
            .map(this.cache::remove)
            .filter(Objects::nonNull)
            .forEach(MetadataAccess::removeAll);
    }
}
