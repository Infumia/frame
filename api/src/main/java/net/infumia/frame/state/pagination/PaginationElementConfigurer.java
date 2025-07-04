package net.infumia.frame.state.pagination;

import net.infumia.frame.context.ContextBase;
import net.infumia.frame.element.item.ElementItemBuilder;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface PaginationElementConfigurer<T> {
    void configure(
        @NotNull ContextBase context,
        @NotNull ElementItemBuilder builder,
        int index,
        int slot,
        @NotNull T value
    );
}
