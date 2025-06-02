package net.infumia.frame.element.pagination;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;
import net.infumia.frame.context.ContextBase;
import org.jetbrains.annotations.NotNull;

public interface SourceProvider<T> extends Function<ContextBase, CompletableFuture<List<T>>> {
    boolean lazy();

    boolean computed();

    boolean provided();

    boolean async();

    final class Immutable<T> implements SourceProvider<T> {

        private final List<T> values;

        public Immutable(@NotNull final List<T> values) {
            this.values = values;
        }

        @NotNull
        @Override
        public CompletableFuture<List<T>> apply(@NotNull final ContextBase context) {
            return CompletableFuture.completedFuture(this.values);
        }

        @Override
        public boolean lazy() {
            return false;
        }

        @Override
        public boolean computed() {
            return false;
        }

        @Override
        public boolean provided() {
            return true;
        }

        @Override
        public boolean async() {
            return false;
        }
    }

    final class Computed<T> implements SourceProvider<T> {

        private final Function<ContextBase, CompletableFuture<List<T>>> provider;
        private final boolean computed;
        private final boolean lazy;
        private final boolean async;

        public Computed(
            @NotNull final Function<ContextBase, CompletableFuture<List<T>>> provider,
            final boolean computed,
            final boolean lazy,
            final boolean async
        ) {
            this.provider = provider;
            this.computed = computed;
            this.lazy = lazy;
            this.async = async;
        }

        public Computed(
            @NotNull final Supplier<CompletableFuture<List<T>>> provider,
            final boolean computed,
            final boolean lazy,
            final boolean async
        ) {
            this(__ -> provider.get(), computed, lazy, async);
        }

        @Override
        public boolean lazy() {
            return this.lazy;
        }

        @Override
        public boolean computed() {
            return this.computed;
        }

        @Override
        public boolean provided() {
            return false;
        }

        @Override
        public boolean async() {
            return this.async;
        }

        @NotNull
        @Override
        public CompletableFuture<List<T>> apply(@NotNull final ContextBase context) {
            return this.provider.apply(context);
        }
    }
}
