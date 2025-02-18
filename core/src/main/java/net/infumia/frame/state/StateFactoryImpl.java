package net.infumia.frame.state;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.function.Supplier;
import net.infumia.frame.Lazy;
import net.infumia.frame.context.ContextBase;
import net.infumia.frame.element.pagination.ElementPagination;
import net.infumia.frame.element.pagination.ElementPaginationBuilder;
import net.infumia.frame.element.pagination.ElementPaginationBuilderImpl;
import net.infumia.frame.element.pagination.ElementPaginationBuilderRich;
import net.infumia.frame.element.pagination.SourceProvider;
import net.infumia.frame.state.pagination.ElementConfigurer;
import net.infumia.frame.state.pagination.StatePagination;
import net.infumia.frame.state.value.StateValueComputed;
import net.infumia.frame.state.value.StateValueImmutable;
import net.infumia.frame.state.value.StateValueInitial;
import net.infumia.frame.state.value.StateValueMutable;
import net.infumia.frame.typedkey.TypedKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StateFactoryImpl implements StateFactory {

    private static final AtomicLong COUNTER = new AtomicLong();

    private final StateRegistry registry;

    public StateFactoryImpl(@NotNull final StateRegistry registry) {
        this.registry = registry;
    }

    @NotNull
    @Override
    public <T> StateInitial<T> createInitialState(@NotNull final TypedKey<T> stateKey) {
        return this.registered(
                new StateInitialImpl<>(
                    StateFactoryImpl.nextStateId(),
                    (host, __) -> new StateValueInitial<>(host, stateKey.key()),
                    stateKey.key()
                )
            );
    }

    @NotNull
    @Override
    public <T> State<T> createState(@NotNull final T initialValue) {
        return this.registered(
                new StateImpl<>(StateFactoryImpl.nextStateId(), (host, __) ->
                    new StateValueImmutable<>(initialValue)
                )
            );
    }

    @NotNull
    @Override
    public <T> StateMutable<T> createMutableState(@Nullable final T initialValue) {
        return this.registered(
                new StateMutableImpl<>(StateFactoryImpl.nextStateId(), (host, __) ->
                    new StateValueMutable<>(initialValue)
                )
            );
    }

    @NotNull
    @Override
    public <T> State<T> createComputedState(@NotNull final Function<ContextBase, T> computation) {
        return this.registered(
                new StateImpl<>(StateFactoryImpl.nextStateId(), (host, __) ->
                    new StateValueComputed<>(() -> computation.apply(host))
                )
            );
    }

    @NotNull
    @Override
    public <T> State<T> createComputedState(@NotNull final Supplier<T> computation) {
        return this.registered(
                new StateImpl<>(StateFactoryImpl.nextStateId(), (host, __) ->
                    new StateValueComputed<>(computation)
                )
            );
    }

    @NotNull
    @Override
    public <T> State<T> createLazyState(@NotNull final Function<ContextBase, T> computation) {
        return this.registered(
                new StateImpl<>(StateFactoryImpl.nextStateId(), (host, __) ->
                    new StateValueComputed<>(Lazy.of(() -> computation.apply(host)))
                )
            );
    }

    @NotNull
    @Override
    public <T> State<T> createLazyState(@NotNull final Supplier<T> computation) {
        return this.registered(
                new StateImpl<>(StateFactoryImpl.nextStateId(), (host, __) ->
                    new StateValueComputed<>(Lazy.of(computation))
                )
            );
    }

    @NotNull
    @Override
    public <T> StatePagination createPaginationState(
        @NotNull final List<T> source,
        @NotNull final ElementConfigurer<T> configurer
    ) {
        return this.buildPaginationState(source).elementConfigurer(configurer).buildPagination();
    }

    @NotNull
    @Override
    public <T> StatePagination createComputedPaginationState(
        @NotNull final Supplier<List<T>> source,
        @NotNull final ElementConfigurer<T> configurer
    ) {
        return this.buildComputedPaginationState(source)
            .elementConfigurer(configurer)
            .buildPagination();
    }

    @NotNull
    @Override
    public <T> StatePagination createComputedPaginationState(
        @NotNull final Function<ContextBase, List<T>> source,
        @NotNull final ElementConfigurer<T> configurer
    ) {
        return this.buildComputedPaginationState(source)
            .elementConfigurer(configurer)
            .buildPagination();
    }

    @NotNull
    @Override
    public <T> StatePagination createComputedAsyncPaginationState(
        @NotNull final Supplier<CompletableFuture<List<T>>> source,
        @NotNull final ElementConfigurer<T> configurer
    ) {
        return this.buildComputedAsyncPaginationState(source)
            .elementConfigurer(configurer)
            .buildPagination();
    }

    @NotNull
    @Override
    public <T> StatePagination createComputedAsyncPaginationState(
        @NotNull final Function<ContextBase, CompletableFuture<List<T>>> source,
        @NotNull final ElementConfigurer<T> configurer
    ) {
        return this.buildComputedAsyncPaginationState(source)
            .elementConfigurer(configurer)
            .buildPagination();
    }

    @NotNull
    @Override
    public <T> StatePagination createLazyPaginationState(
        @NotNull final Supplier<List<T>> source,
        @NotNull final ElementConfigurer<T> configurer
    ) {
        return this.buildLazyPaginationState(source)
            .elementConfigurer(configurer)
            .buildPagination();
    }

    @NotNull
    @Override
    public <T> StatePagination createLazyPaginationState(
        @NotNull final Function<ContextBase, List<T>> source,
        @NotNull final ElementConfigurer<T> configurer
    ) {
        return this.buildLazyPaginationState(source)
            .elementConfigurer(configurer)
            .buildPagination();
    }

    @NotNull
    @Override
    public <T> StatePagination createLazyAsyncPaginationState(
        @NotNull final Supplier<CompletableFuture<List<T>>> source,
        @NotNull final ElementConfigurer<T> configurer
    ) {
        return this.buildLazyAsyncPaginationState(source)
            .elementConfigurer(configurer)
            .buildPagination();
    }

    @NotNull
    @Override
    public <T> StatePagination createLazyAsyncPaginationState(
        @NotNull final Function<ContextBase, CompletableFuture<List<T>>> source,
        @NotNull final ElementConfigurer<T> configurer
    ) {
        return this.buildLazyAsyncPaginationState(source)
            .elementConfigurer(configurer)
            .buildPagination();
    }

    @NotNull
    @Override
    public <T> ElementPaginationBuilder<T> buildPaginationState(@NotNull final List<T> source) {
        return new ElementPaginationBuilderImpl<>(
            new SourceProvider.Immutable<>(source),
            this::createPaginationState
        );
    }

    @NotNull
    @Override
    public <T> ElementPaginationBuilder<T> buildComputedPaginationState(
        @NotNull final Supplier<List<T>> source
    ) {
        return new ElementPaginationBuilderImpl<>(
            new SourceProvider.Computed<>(
                () -> CompletableFuture.completedFuture(source.get()),
                true,
                false
            ),
            this::createPaginationState
        );
    }

    @NotNull
    @Override
    public <T> ElementPaginationBuilder<T> buildComputedPaginationState(
        @NotNull final Function<ContextBase, List<T>> source
    ) {
        return new ElementPaginationBuilderImpl<>(
            new SourceProvider.Computed<>(
                context -> CompletableFuture.completedFuture(source.apply(context)),
                true,
                false
            ),
            this::createPaginationState
        );
    }

    @NotNull
    @Override
    public <T> ElementPaginationBuilder<T> buildComputedAsyncPaginationState(
        @NotNull final Supplier<CompletableFuture<List<T>>> source
    ) {
        return new ElementPaginationBuilderImpl<>(
            new SourceProvider.Computed<>(source, true, false),
            this::createPaginationState
        );
    }

    @NotNull
    @Override
    public <T> ElementPaginationBuilder<T> buildComputedAsyncPaginationState(
        @NotNull final Function<ContextBase, CompletableFuture<List<T>>> source
    ) {
        return new ElementPaginationBuilderImpl<>(
            new SourceProvider.Computed<>(source, true, false),
            this::createPaginationState
        );
    }

    @NotNull
    @Override
    public <T> ElementPaginationBuilder<T> buildLazyPaginationState(
        @NotNull final Supplier<List<T>> source
    ) {
        return new ElementPaginationBuilderImpl<>(
            new SourceProvider.Computed<>(
                () -> CompletableFuture.completedFuture(source.get()),
                false,
                true
            ),
            this::createPaginationState
        );
    }

    @NotNull
    @Override
    public <T> ElementPaginationBuilder<T> buildLazyPaginationState(
        @NotNull final Function<ContextBase, List<T>> source
    ) {
        return new ElementPaginationBuilderImpl<>(
            new SourceProvider.Computed<>(
                context -> CompletableFuture.completedFuture(source.apply(context)),
                false,
                true
            ),
            this::createPaginationState
        );
    }

    @NotNull
    @Override
    public <T> ElementPaginationBuilder<T> buildLazyAsyncPaginationState(
        @NotNull final Supplier<CompletableFuture<List<T>>> source
    ) {
        return new ElementPaginationBuilderImpl<>(
            new SourceProvider.Computed<>(source, false, true),
            this::createPaginationState
        );
    }

    @NotNull
    @Override
    public <T> ElementPaginationBuilder<T> buildLazyAsyncPaginationState(
        @NotNull final Function<ContextBase, CompletableFuture<List<T>>> source
    ) {
        return new ElementPaginationBuilderImpl<>(
            new SourceProvider.Computed<>(source, false, true),
            this::createPaginationState
        );
    }

    @NotNull
    private <T> StatePagination createPaginationState(
        @NotNull final ElementPaginationBuilder<T> builder
    ) {
        return this.registered(
                new StatePaginationImpl(StateFactoryImpl.nextStateId(), (host, state) -> {
                    final ElementPaginationBuilderRich<T> b = (ElementPaginationBuilderRich<
                            T
                        >) builder;
                    b.associated(state);
                    return new StateValueImmutable<>((ElementPagination) b.build(host));
                })
            );
    }

    @NotNull
    private <T, S extends StateRich<T>> S registered(@NotNull final S state) {
        this.registry.register(state);
        return state;
    }

    private static long nextStateId() {
        return StateFactoryImpl.COUNTER.getAndIncrement();
    }
}
