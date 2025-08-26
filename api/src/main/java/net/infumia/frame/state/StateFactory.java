package net.infumia.frame.state;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;
import net.infumia.frame.context.ContextBase;
import net.infumia.frame.element.pagination.ElementPaginationBuilder;
import net.infumia.frame.typedkey.TypedKey;
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
