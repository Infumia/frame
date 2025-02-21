package net.infumia.frame.element.pagination;

import java.util.function.BiConsumer;
import java.util.function.Function;
import net.infumia.frame.context.ContextBase;
import net.infumia.frame.element.ElementBuilderImpl;
import net.infumia.frame.element.item.ElementItemBuilder;
import net.infumia.frame.state.State;
import net.infumia.frame.state.pagination.ElementConfigurer;
import net.infumia.frame.state.pagination.StatePagination;
import org.jetbrains.annotations.NotNull;

public final class ElementPaginationBuilderImpl<T>
    extends ElementBuilderImpl
    implements ElementPaginationBuilderRich<T> {

    final SourceProvider<T> sourceProvider;
    final Function<ElementPaginationBuilder<T>, StatePagination> stateFactory;
    char layout = '0';
    BiConsumer<ContextBase, ElementPagination> onPageSwitch;
    ElementConfigurer<T> elementConfigurer;
    State<ElementPagination> associated;

    public ElementPaginationBuilderImpl(
        @NotNull final SourceProvider<T> sourceProvider,
        @NotNull final Function<ElementPaginationBuilder<T>, StatePagination> stateFactory
    ) {
        this.sourceProvider = sourceProvider;
        this.stateFactory = stateFactory;
    }

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
    public ElementPaginationBuilder<T> associated(
        @NotNull final State<ElementPagination> associated
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
    public ElementPagination build(@NotNull final ContextBase parent) {
        return new ElementPaginationImpl<>(this, parent);
    }
}
