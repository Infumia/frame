package net.infumia.inv.api.element.provider.slot;

import net.infumia.inv.api.context.ContextBase;
import org.jetbrains.annotations.NotNull;

public interface ElementSlotProviderPosition {
    int provideRow(@NotNull ContextBase ctx);

    int provideColumn(@NotNull ContextBase ctx);
}
