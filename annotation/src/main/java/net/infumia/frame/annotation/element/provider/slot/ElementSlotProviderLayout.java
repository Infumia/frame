package net.infumia.frame.element.provider.slot;

import net.infumia.frame.context.ContextBase;
import org.jetbrains.annotations.NotNull;

public interface ElementSlotProviderLayout {
    char provideLayout(@NotNull ContextBase ctx);
}
