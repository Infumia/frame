package net.infumia.inv.api.element.provider.slot;

import net.infumia.inv.api.context.ContextBase;
import org.jetbrains.annotations.NotNull;

public interface ElementSlotProviderLayout {
    char provideLayout(@NotNull ContextBase ctx);
}
