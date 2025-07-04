package net.infumia.frame.metadata;

import net.infumia.frame.Preconditions;
import org.bukkit.entity.Entity;
import org.bukkit.metadata.Metadatable;
import org.jetbrains.annotations.NotNull;

public final class CacheKeyExtractorEntityUniqueId implements CacheKeyExtractor {

    @NotNull
    @Override
    public String apply(@NotNull final Metadatable metadatable) {
        Preconditions.argument(
            metadatable instanceof Entity,
            "Only Entity type supported for metadata cache key extraction!"
        );
        return ((Entity) metadatable).getUniqueId().toString();
    }
}
