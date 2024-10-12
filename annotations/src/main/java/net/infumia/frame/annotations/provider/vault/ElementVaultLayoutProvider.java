package net.infumia.frame.annotations.provider.vault;

import net.infumia.frame.context.ContextBase;
import org.jetbrains.annotations.NotNull;

public interface ElementVaultLayoutProvider {
    char provideLayout(@NotNull ContextBase ctx);
}
