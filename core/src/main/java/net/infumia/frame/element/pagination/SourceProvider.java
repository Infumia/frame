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
    }

    final class Computed<T> implements SourceProvider<T> {

        private final Function<ContextBase, CompletableFuture<List<T>>> provider;
        private final boolean computed;
        private final boolean lazy;

        public Computed(
            @NotNull final Function<ContextBase, CompletableFuture<List<T>>> provider,
            final boolean computed,
            final boolean lazy
        ) {
            this.provider = provider;
            this.computed = computed;
            this.lazy = lazy;
        }

        public Computed(
            @NotNull final Supplier<CompletableFuture<List<T>>> provider,
            final boolean computed,
            final boolean lazy
        ) {
            this(base -> provider.get(), computed, lazy);
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

        @NotNull
        @Override
        public CompletableFuture<List<T>> apply(@NotNull final ContextBase context) {
            return this.provider.apply(context);
        }
    }
}
