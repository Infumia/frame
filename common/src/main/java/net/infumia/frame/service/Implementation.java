package net.infumia.frame.service;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Implementation<Context, Result> {
    @NotNull
    static <Context, Result> Implementation<Context, Result> register(
        @NotNull final Service<Context, Result> service,
        @Nullable final Collection<Predicate<Context>> filters
    ) {
        return new Register<>(service, filters);
    }

    @NotNull
    static <Context, Result> Implementation<Context, Result> register(
        @NotNull final Service<Context, Result> service
    ) {
        return Implementation.register(service, null);
    }

    @NotNull
    static <Context, Result> Implementation<Context, Result> registerBefore(
        @NotNull final String serviceKey,
        @NotNull final Service<Context, Result> service,
        @Nullable final Collection<Predicate<Context>> filters
    ) {
        return new RegisterBefore<>(serviceKey, service, filters);
    }

    @NotNull
    static <Context, Result> Implementation<Context, Result> registerBefore(
        @NotNull final String serviceKey,
        @NotNull final Service<Context, Result> service
    ) {
        return Implementation.registerBefore(serviceKey, service, null);
    }

    @NotNull
    static <Context, Result> Implementation<Context, Result> registerAfter(
        @NotNull final String serviceKey,
        @NotNull final Service<Context, Result> service,
        @Nullable final Collection<Predicate<Context>> filters
    ) {
        return new RegisterAfter<>(serviceKey, service, filters);
    }

    @NotNull
    static <Context, Result> Implementation<Context, Result> registerAfter(
        @NotNull final String serviceKey,
        @NotNull final Service<Context, Result> service
    ) {
        return Implementation.registerAfter(serviceKey, service, null);
    }

    @NotNull
    static <Context, Result> Implementation<Context, Result> replace(
        @NotNull final String serviceKey,
        @NotNull final UnaryOperator<Service<Context, Result>> service,
        @Nullable final Collection<Predicate<Context>> filters
    ) {
        return new Replace<>(serviceKey, service, filters);
    }

    @NotNull
    static <Context, Result> Implementation<Context, Result> replace(
        @NotNull final String serviceKey,
        @NotNull final UnaryOperator<Service<Context, Result>> service
    ) {
        return Implementation.replace(serviceKey, service, null);
    }

    void handle(@NotNull ServiceRepository<Context, Result> repository);
}
