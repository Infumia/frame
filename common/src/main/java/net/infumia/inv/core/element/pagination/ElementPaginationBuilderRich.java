package net.infumia.inv.core.element.pagination;

import net.infumia.inv.api.context.ContextBase;
import net.infumia.inv.api.element.pagination.ElementPagination;
import net.infumia.inv.api.element.pagination.ElementPaginationBuilder;
import net.infumia.inv.core.element.ElementBuilderRich;
import net.infumia.inv.core.state.StateRich;
import org.jetbrains.annotations.NotNull;

public interface ElementPaginationBuilderRich<T>
    extends ElementBuilderRich, ElementPaginationBuilder<T> {
    @NotNull
    ElementPaginationBuilderRich<T> associated(@NotNull StateRich<ElementPagination> associated);

    @NotNull
    @Override
    ElementPaginationRich<T> build(@NotNull ContextBase parent);
}
