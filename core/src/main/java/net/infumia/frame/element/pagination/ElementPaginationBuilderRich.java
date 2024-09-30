package net.infumia.frame.element.pagination;

import net.infumia.frame.context.ContextBase;
import net.infumia.frame.element.ElementBuilderRich;
import net.infumia.frame.state.StateRich;
import org.jetbrains.annotations.NotNull;

public interface ElementPaginationBuilderRich<T>
    extends ElementBuilderRich, ElementPaginationBuilder<T> {
    @NotNull
    ElementPaginationBuilderRich<T> associated(@NotNull StateRich<ElementPagination> associated);

    @NotNull
    @Override
    ElementPaginationRich<T> build(@NotNull ContextBase parent);
}
