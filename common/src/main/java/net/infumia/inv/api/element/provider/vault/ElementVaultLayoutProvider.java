package net.infumia.inv.api.element.provider.vault;

import net.infumia.inv.api.context.ContextBase;
import org.jetbrains.annotations.NotNull;

public interface ElementVaultLayoutProvider {
    char provideLayout(@NotNull ContextBase ctx);
}
