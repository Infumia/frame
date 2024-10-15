package net.infumia.frame.annotations.provider.slot;

import net.infumia.frame.context.ContextBase;
import org.jetbrains.annotations.NotNull;

public interface ElementSlotProviderPosition {
    int provideRow(@NotNull ContextBase ctx);

    int provideColumn(@NotNull ContextBase ctx);
}
