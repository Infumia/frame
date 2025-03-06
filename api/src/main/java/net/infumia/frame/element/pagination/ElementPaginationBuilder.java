package net.infumia.frame.element.pagination;

import java.util.function.BiConsumer;
import net.infumia.frame.context.ContextBase;
import net.infumia.frame.element.ElementBuilder;
import net.infumia.frame.element.item.ElementItemBuilder;
import net.infumia.frame.state.pagination.PaginationElementConfigurer;
import net.infumia.frame.state.pagination.StatePagination;
import org.jetbrains.annotations.NotNull;

public interface ElementPaginationBuilder<Type>
    extends ElementBuilder<ElementPaginationBuilder<Type>> {
    @NotNull
    ElementPaginationBuilder<Type> layout(char layout);

    @NotNull
    ElementPaginationBuilder<Type> onPageSwitch(
        @NotNull BiConsumer<ContextBase, ElementPagination> onPageSwitch
    );

    @NotNull
    ElementPaginationBuilder<Type> elementConfigurer(
        @NotNull BiConsumer<ElementItemBuilder, Type> configurer
    );

    @NotNull
    ElementPaginationBuilder<Type> elementConfigurer(
        @NotNull PaginationElementConfigurer<Type> configurer
    );

    @NotNull
    StatePagination buildPagination();
}
