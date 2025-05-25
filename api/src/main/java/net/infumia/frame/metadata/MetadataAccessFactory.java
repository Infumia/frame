package net.infumia.frame.metadata;

import java.util.Collection;
import org.bukkit.metadata.Metadatable;
import org.jetbrains.annotations.NotNull;

public interface MetadataAccessFactory {
    @NotNull
    MetadataAccess getOrCreate(@NotNull Metadatable metadatable);

    void clearCache(@NotNull Collection<? extends Metadatable> list);
}
