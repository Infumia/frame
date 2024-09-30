package net.infumia.inv.api.state.pagination;

import net.infumia.inv.api.context.ContextBase;
import net.infumia.inv.api.element.ElementItemBuilder;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface ElementConfigurer<T> {
    void configure(
        @NotNull ContextBase context,
        @NotNull ElementItemBuilder builder,
        int index,
        int slot,
        @NotNull T value
    );
}
