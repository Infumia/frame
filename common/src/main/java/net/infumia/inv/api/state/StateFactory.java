package net.infumia.inv.api.state;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;
import net.infumia.inv.api.context.ContextBase;
import net.infumia.inv.api.element.pagination.ElementPaginationBuilder;
import net.infumia.inv.api.state.pagination.ElementConfigurer;
import net.infumia.inv.api.state.pagination.StatePagination;
import net.infumia.inv.api.typedkey.TypedKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface StateFactory {
    @NotNull
    <T> StateInitial<T> createInitialState(@NotNull TypedKey<T> stateKey);

    @NotNull
    <T> State<T> createState(@NotNull T initialValue);

    @NotNull
    <T> StateMutable<T> createMutableState(@Nullable T initialValue);

    @NotNull
    <T> State<T> createComputedState(@NotNull Function<ContextBase, T> computation);

    @NotNull
    <T> State<T> createComputedState(@NotNull Supplier<T> computation);

    @NotNull
    <T> State<T> createLazyState(@NotNull Function<ContextBase, T> computation);

    @NotNull
    <T> State<T> createLazyState(@NotNull Supplier<T> computation);

    @NotNull
    <T> StatePagination createPaginationState(
        @NotNull List<T> source,
        @NotNull ElementConfigurer<T> configurer
    );

    @NotNull
    <T> StatePagination createComputedPaginationState(
        @NotNull Supplier<List<T>> source,
        @NotNull ElementConfigurer<T> configurer
    );

    @NotNull
    <T> StatePagination createComputedPaginationState(
        @NotNull Function<ContextBase, List<T>> source,
        @NotNull ElementConfigurer<T> configurer
    );

    @NotNull
    <T> StatePagination createComputedAsyncPaginationState(
        @NotNull Supplier<CompletableFuture<List<T>>> source,
        @NotNull ElementConfigurer<T> configurer
    );

    @NotNull
    <T> StatePagination createComputedAsyncPaginationState(
        @NotNull Function<ContextBase, CompletableFuture<List<T>>> source,
        @NotNull ElementConfigurer<T> configurer
    );

    @NotNull
    <T> StatePagination createLazyPaginationState(
        @NotNull Supplier<List<T>> source,
        @NotNull ElementConfigurer<T> configurer
    );

    @NotNull
    <T> StatePagination createLazyPaginationState(
        @NotNull Function<ContextBase, List<T>> source,
        @NotNull ElementConfigurer<T> configurer
    );

    @NotNull
    <T> StatePagination createLazyAsyncPaginationState(
        @NotNull Supplier<CompletableFuture<List<T>>> source,
        @NotNull ElementConfigurer<T> configurer
    );

    @NotNull
    <T> StatePagination createLazyAsyncPaginationState(
        @NotNull Function<ContextBase, CompletableFuture<List<T>>> source,
        @NotNull ElementConfigurer<T> configurer
    );

    @NotNull
    <T> ElementPaginationBuilder<T> buildPaginationState(@NotNull List<T> source);

    @NotNull
    <T> ElementPaginationBuilder<T> buildComputedPaginationState(@NotNull Supplier<List<T>> source);

    @NotNull
    <T> ElementPaginationBuilder<T> buildComputedPaginationState(
        @NotNull Function<ContextBase, List<T>> source
    );

    @NotNull
    <T> ElementPaginationBuilder<T> buildComputedAsyncPaginationState(
        @NotNull Supplier<CompletableFuture<List<T>>> source
    );

    @NotNull
    <T> ElementPaginationBuilder<T> buildComputedAsyncPaginationState(
        @NotNull Function<ContextBase, CompletableFuture<List<T>>> source
    );

    @NotNull
    <T> ElementPaginationBuilder<T> buildLazyPaginationState(@NotNull Supplier<List<T>> source);

    @NotNull
    <T> ElementPaginationBuilder<T> buildLazyPaginationState(
        @NotNull Function<ContextBase, List<T>> source
    );

    @NotNull
    <T> ElementPaginationBuilder<T> buildLazyAsyncPaginationState(
        @NotNull Supplier<CompletableFuture<List<T>>> source
    );

    @NotNull
    <T> ElementPaginationBuilder<T> buildLazyAsyncPaginationState(
        @NotNull Function<ContextBase, CompletableFuture<List<T>>> source
    );
}
