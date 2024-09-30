package net.infumia.inv.api.pipeline;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import net.infumia.inv.api.service.Implementation;
import net.infumia.inv.api.service.Service;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PipelineBase<B extends PipelineContext, R, Self extends PipelineBase<B, R, Self>> {
    @NotNull
    Self apply(@NotNull Implementation<B, R> operation);

    @NotNull
    CompletableFuture<R> completeWith(@NotNull B context);

    @NotNull
    CompletableFuture<R> completeWithAsync(@NotNull B context);

    @NotNull
    default Self register(@NotNull final Service<B, R> service) {
        return this.apply(Implementation.register(service));
    }

    @NotNull
    default Self register(
        @NotNull final Service<B, R> service,
        @Nullable final Collection<Predicate<B>> filters
    ) {
        return this.apply(Implementation.register(service, filters));
    }

    @NotNull
    default Self replace(
        @NotNull final String serviceKey,
        @NotNull final UnaryOperator<Service<B, R>> operation
    ) {
        return this.apply(Implementation.replace(serviceKey, operation));
    }

    @NotNull
    default Self replace(
        @NotNull final String serviceKey,
        @NotNull final UnaryOperator<Service<B, R>> operation,
        @Nullable final Collection<Predicate<B>> filters
    ) {
        return this.apply(Implementation.replace(serviceKey, operation, filters));
    }

    @NotNull
    default Self registerAfter(
        @NotNull final String serviceKey,
        @NotNull final Service<B, R> service
    ) {
        return this.apply(Implementation.registerAfter(serviceKey, service));
    }

    @NotNull
    default Self registerAfter(
        @NotNull final String serviceKey,
        @NotNull final Service<B, R> service,
        @Nullable final Collection<Predicate<B>> filters
    ) {
        return this.apply(Implementation.registerAfter(serviceKey, service, filters));
    }

    @NotNull
    default Self registerBefore(
        @NotNull final String serviceKey,
        @NotNull final Service<B, R> service
    ) {
        return this.apply(Implementation.registerBefore(serviceKey, service));
    }

    @NotNull
    default Self registerBefore(
        @NotNull final String serviceKey,
        @NotNull final Service<B, R> service,
        @Nullable final Collection<Predicate<B>> filters
    ) {
        return this.apply(Implementation.registerBefore(serviceKey, service, filters));
    }
}
