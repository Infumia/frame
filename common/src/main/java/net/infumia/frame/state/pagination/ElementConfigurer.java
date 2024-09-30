package net.infumia.frame.state.pagination;

import net.infumia.frame.context.ContextBase;
import net.infumia.frame.element.ElementItemBuilder;
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
