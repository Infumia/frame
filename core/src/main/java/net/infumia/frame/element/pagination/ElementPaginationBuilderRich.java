package net.infumia.frame.element.pagination;

import net.infumia.frame.context.ContextBase;
import net.infumia.frame.element.ElementBuilderRich;
import net.infumia.frame.state.State;
import org.jetbrains.annotations.NotNull;

public interface ElementPaginationBuilderRich<Type>
    extends ElementBuilderRich, ElementPaginationBuilder<Type> {
    void associated(@NotNull State<ElementPagination> associated);

    @NotNull
    @Override
    ElementPagination build(@NotNull ContextBase parent);
}
