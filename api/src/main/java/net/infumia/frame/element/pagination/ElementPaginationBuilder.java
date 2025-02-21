package net.infumia.frame.element.pagination;

import java.util.function.BiConsumer;
import net.infumia.frame.context.ContextBase;
import net.infumia.frame.element.ElementBuilder;
import net.infumia.frame.element.item.ElementItemBuilder;
import net.infumia.frame.state.pagination.ElementConfigurer;
import net.infumia.frame.state.pagination.StatePagination;
import org.jetbrains.annotations.NotNull;

public interface ElementPaginationBuilder<T> extends ElementBuilder {
    @NotNull
    ElementPaginationBuilder<T> layout(char layout);

    @NotNull
    ElementPaginationBuilder<T> onPageSwitch(
        @NotNull BiConsumer<ContextBase, ElementPagination> onPageSwitch
    );

    @NotNull
    ElementPaginationBuilder<T> elementConfigurer(
        @NotNull BiConsumer<ElementItemBuilder, T> configurer
    );

    @NotNull
    ElementPaginationBuilder<T> elementConfigurer(@NotNull ElementConfigurer<T> configurer);

    @NotNull
    StatePagination buildPagination();
}
