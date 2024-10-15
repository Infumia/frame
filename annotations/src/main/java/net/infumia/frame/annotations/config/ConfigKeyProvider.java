package net.infumia.frame.annotations.config;

import net.infumia.frame.context.ContextBase;
import org.jetbrains.annotations.NotNull;

public interface ConfigKeyProvider {
    @NotNull
    String provideConfigKey(@NotNull ContextBase ctx);
}
