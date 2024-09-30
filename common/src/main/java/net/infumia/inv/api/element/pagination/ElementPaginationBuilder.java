package net.infumia.inv.api.element.pagination;

import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import net.infumia.inv.api.context.ContextBase;
import net.infumia.inv.api.context.view.ContextRender;
import net.infumia.inv.api.element.ElementBuilder;
import net.infumia.inv.api.element.ElementItemBuilder;
import net.infumia.inv.api.state.State;
import net.infumia.inv.api.state.pagination.ElementConfigurer;
import net.infumia.inv.api.state.pagination.StatePagination;
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

    @NotNull
    @Override
    ElementPaginationBuilder<T> cancelOnClick();

    @NotNull
    @Override
    ElementPaginationBuilder<T> closeOnClick();

    @NotNull
    @Override
    ElementPaginationBuilder<T> updateOnClick();

    @NotNull
    @Override
    ElementPaginationBuilder<T> cancelOnClick(boolean cancelOnClick);

    @NotNull
    @Override
    ElementPaginationBuilder<T> closeOnClick(boolean cancelOnClick);

    @NotNull
    @Override
    ElementPaginationBuilder<T> updateOnClick(boolean updateOnClick);

    @NotNull
    @Override
    ElementPaginationBuilder<T> updateOnStateChange(
        @NotNull State<?> state,
        @NotNull State<?>... otherStates
    );

    @NotNull
    @Override
    ElementPaginationBuilder<T> updateOnStateAccess(
        @NotNull State<?> state,
        @NotNull State<?>... otherStates
    );

    @NotNull
    @Override
    ElementPaginationBuilder<T> displayIf(@NotNull Predicate<ContextRender> condition);

    @NotNull
    @Override
    ElementPaginationBuilder<T> displayIf(@NotNull BooleanSupplier condition);

    @NotNull
    @Override
    ElementPaginationBuilder<T> hideIf(@NotNull Predicate<ContextRender> condition);

    @NotNull
    @Override
    ElementPaginationBuilder<T> hideIf(@NotNull BooleanSupplier condition);
}
