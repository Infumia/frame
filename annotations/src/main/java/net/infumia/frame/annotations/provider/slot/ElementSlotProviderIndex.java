package net.infumia.frame.annotations.provider.slot;

import net.infumia.frame.context.ContextBase;
import org.jetbrains.annotations.NotNull;

public interface ElementSlotProviderIndex {
    int provideIndex(@NotNull ContextBase ctx);
}
