package net.infumia.frame.service;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import net.infumia.frame.Preconditions;

public interface Implementation<Context, Result> {
    static <Context, Result> Implementation<Context, Result> remove(final String serviceKey) {
        return new Remove<>(Preconditions.argumentNotNull(serviceKey, "serviceKey"));
    }

    static <Context, Result> Implementation<Context, Result> register(
        final Service<Context, Result> service,
        final Collection<Predicate<Context>> filters
    ) {
        return new Register<>(Preconditions.argumentNotNull(service, "service"), filters);
    }

    static <Context, Result> Implementation<Context, Result> register(
        final Service<Context, Result> service
    ) {
        return Implementation.register(Preconditions.argumentNotNull(service, "service"), null);
    }

    static <Context, Result> Implementation<Context, Result> registerBefore(
        final String serviceKey,
        final Service<Context, Result> service,
        final Collection<Predicate<Context>> filters
    ) {
        return new RegisterBefore<>(
            Preconditions.argumentNotNull(serviceKey, "serviceKey"),
            Preconditions.argumentNotNull(service, "service"),
            filters
        );
    }

    static <Context, Result> Implementation<Context, Result> registerBefore(
        final String serviceKey,
        final Service<Context, Result> service
    ) {
        return Implementation.registerBefore(
            Preconditions.argumentNotNull(serviceKey, "serviceKey"),
            Preconditions.argumentNotNull(service, "service"),
            null
        );
    }

    static <Context, Result> Implementation<Context, Result> registerAfter(
        final String serviceKey,
        final Service<Context, Result> service,
        final Collection<Predicate<Context>> filters
    ) {
        return new RegisterAfter<>(
            Preconditions.argumentNotNull(serviceKey, "serviceKey"),
            Preconditions.argumentNotNull(service, "service"),
            filters
        );
    }

    static <Context, Result> Implementation<Context, Result> registerAfter(
        final String serviceKey,
        final Service<Context, Result> service
    ) {
        return Implementation.registerAfter(
            Preconditions.argumentNotNull(serviceKey, "serviceKey"),
            Preconditions.argumentNotNull(service, "service"),
            null
        );
    }

    static <Context, Result> Implementation<Context, Result> replace(
        final String serviceKey,
        final UnaryOperator<Service<Context, Result>> service,
        final Collection<Predicate<Context>> filters
    ) {
        return new Replace<>(
            Preconditions.argumentNotNull(serviceKey, "serviceKey"),
            Preconditions.argumentNotNull(service, "service"),
            filters
        );
    }

    static <Context, Result> Implementation<Context, Result> replace(
        final String serviceKey,
        final UnaryOperator<Service<Context, Result>> service
    ) {
        return Implementation.replace(
            Preconditions.argumentNotNull(serviceKey, "serviceKey"),
            Preconditions.argumentNotNull(service, "service"),
            null
        );
    }

    void handle(ServiceRepository<Context, Result> repository);
}
