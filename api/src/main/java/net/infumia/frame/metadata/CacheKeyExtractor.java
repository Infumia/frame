package net.infumia.frame.metadata;

import java.util.function.Function;
import org.bukkit.metadata.Metadatable;

@FunctionalInterface
public interface CacheKeyExtractor extends Function<Metadatable, String> {}
