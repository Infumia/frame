package net.infumia.frame.annotation.config;

import net.infumia.frame.api.context.ContextBase;
import org.jetbrains.annotations.NotNull;

public interface ConfigKeyProvider {
    @NotNull
    String provideConfigKey(@NotNull ContextBase ctx);
}
