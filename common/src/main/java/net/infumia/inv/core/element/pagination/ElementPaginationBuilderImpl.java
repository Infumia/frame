package net.infumia.inv.core.element.pagination;

import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Predicate;
import net.infumia.inv.api.context.ContextBase;
import net.infumia.inv.api.context.view.ContextRender;
import net.infumia.inv.api.element.ElementItemBuilder;
import net.infumia.inv.api.element.pagination.ElementPagination;
import net.infumia.inv.api.element.pagination.ElementPaginationBuilder;
import net.infumia.inv.api.state.State;
import net.infumia.inv.api.state.pagination.ElementConfigurer;
import net.infumia.inv.api.state.pagination.StatePagination;
import net.infumia.inv.core.element.ElementBuilderImpl;
import net.infumia.inv.core.element.ElementRich;
import net.infumia.inv.core.state.StateRich;
import org.jetbrains.annotations.NotNull;

public final class ElementPaginationBuilderImpl<T>
    extends ElementBuilderImpl
    implements ElementPaginationBuilderRich<T> {

    final SourceProvider<T> sourceProvider;
    final Function<ElementPaginationBuilderRich<T>, StatePagination> stateFactory;
    char layout = '0';
    BiConsumer<ContextBase, ElementPagination> onPageSwitch;
    ElementConfigurer<T> elementConfigurer;
    StateRich<ElementPagination> associated;

    ElementPaginationBuilderImpl(@NotNull final ElementPaginationImpl<T> element) {
        super(element);
        this.sourceProvider = element.sourceProvider;
        this.stateFactory = element.stateFactory;
        this.layout = element.layout;
        this.onPageSwitch = element.onPageSwitch;
        this.elementConfigurer = element.elementConfigurer;
    }

    @NotNull
    @Override
    public ElementPaginationBuilderRich<T> associated(
        @NotNull final StateRich<ElementPagination> associated
    ) {
        this.associated = associated;
        return this;
    }

    @NotNull
    @Override
    public ElementPaginationBuilder<T> layout(final char layout) {
        this.layout = layout;
        return this;
    }

    @NotNull
    @Override
    public ElementPaginationBuilder<T> onPageSwitch(
        @NotNull final BiConsumer<ContextBase, ElementPagination> onPageSwitch
    ) {
        this.onPageSwitch = onPageSwitch;
        return this;
    }

    @NotNull
    @Override
    public ElementPaginationBuilder<T> elementConfigurer(
        @NotNull final BiConsumer<ElementItemBuilder, T> configurer
    ) {
        return this.elementConfigurer((__, b, ___, ____, value) -> configurer.accept(b, value));
    }

    @NotNull
    @Override
    public ElementPaginationBuilder<T> elementConfigurer(
        @NotNull final ElementConfigurer<T> configurer
    ) {
        this.elementConfigurer = configurer;
        return this;
    }

    @NotNull
    @Override
    public StatePagination buildPagination() {
        return this.stateFactory.apply(this);
    }

    @NotNull
    @Override
    public ElementPaginationBuilderRich<T> root(final @NotNull ElementRich root) {
        super.root(root);
        return this;
    }

    @NotNull
    @Override
    public ElementPaginationRich<T> build(@NotNull final ContextBase parent) {
        return new ElementPaginationImpl<>(this, parent);
    }

    @NotNull
    @Override
    public ElementPaginationBuilder<T> cancelOnClick() {
        super.cancelOnClick();
        return this;
    }

    @NotNull
    @Override
    public ElementPaginationBuilder<T> closeOnClick() {
        super.closeOnClick();
        return this;
    }

    @NotNull
    @Override
    public ElementPaginationBuilder<T> updateOnClick() {
        super.updateOnClick();
        return this;
    }

    @NotNull
    @Override
    public ElementPaginationBuilder<T> cancelOnClick(final boolean cancelOnClick) {
        super.cancelOnClick(cancelOnClick);
        return this;
    }

    @NotNull
    @Override
    public ElementPaginationBuilder<T> closeOnClick(final boolean cancelOnClick) {
        super.closeOnClick(cancelOnClick);
        return this;
    }

    @NotNull
    @Override
    public ElementPaginationBuilder<T> updateOnClick(final boolean updateOnClick) {
        super.updateOnClick(updateOnClick);
        return this;
    }

    @NotNull
    @Override
    public ElementPaginationBuilder<T> updateOnStateChange(
        @NotNull final State<?> state,
        @NotNull final State<?> @NotNull... otherStates
    ) {
        super.updateOnStateChange(state, otherStates);
        return this;
    }

    @NotNull
    @Override
    public ElementPaginationBuilder<T> updateOnStateAccess(
        @NotNull final State<?> state,
        @NotNull final State<?> @NotNull... otherStates
    ) {
        super.updateOnStateAccess(state, otherStates);
        return this;
    }

    @NotNull
    @Override
    public ElementPaginationBuilder<T> displayIf(
        @NotNull final Predicate<ContextRender> condition
    ) {
        super.displayIf(condition);
        return this;
    }

    @NotNull
    @Override
    public ElementPaginationBuilder<T> displayIf(@NotNull final BooleanSupplier condition) {
        super.displayIf(condition);
        return this;
    }

    @NotNull
    @Override
    public ElementPaginationBuilder<T> hideIf(@NotNull final Predicate<ContextRender> condition) {
        super.hideIf(condition);
        return this;
    }

    @NotNull
    @Override
    public ElementPaginationBuilder<T> hideIf(@NotNull final BooleanSupplier condition) {
        super.hideIf(condition);
        return this;
    }

    public ElementPaginationBuilderImpl(
        @NotNull final SourceProvider<T> sourceProvider,
        @NotNull final Function<ElementPaginationBuilderRich<T>, StatePagination> stateFactory
    ) {
        this.sourceProvider = sourceProvider;
        this.stateFactory = stateFactory;
    }
}
