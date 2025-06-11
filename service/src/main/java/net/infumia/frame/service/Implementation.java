package net.infumia.frame.service;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import net.infumia.frame.Preconditions;

/**
 * Represents an operation that can be applied to a {@link ServiceRepository}.
 *
 * @param <Context> the context type.
 * @param <Result> the result type.
 */
public interface Implementation<Context, Result> {
    /**
     * Creates a new remove operation.
     *
     * @param serviceKey the key of the service to remove.
     * @param <Context> the context type.
     * @param <Result> the result type.
     * @return a new remove operation.
     */
    static <Context, Result> Implementation<Context, Result> remove(final String serviceKey) {
        return new Remove<>(Preconditions.argumentNotNull(serviceKey, "serviceKey"));
    }

    /**
     * Creates a new register operation.
     *
     * @param service the service to register.
     * @param filters the filters to apply to the service.
     * @param <Context> the context type.
     * @param <Result> the result type.
     * @return a new register operation.
     */
    static <Context, Result> Implementation<Context, Result> register(
        final Service<Context, Result> service,
        final Collection<Predicate<Context>> filters
    ) {
        return new Register<>(Preconditions.argumentNotNull(service, "service"), filters);
    }

    /**
     * Creates a new register operation.
     *
     * @param service the service to register.
     * @param <Context> the context type.
     * @param <Result> the result type.
     * @return a new register operation.
     */
    static <Context, Result> Implementation<Context, Result> register(
        final Service<Context, Result> service
    ) {
        return Implementation.register(Preconditions.argumentNotNull(service, "service"), null);
    }

    /**
     * Creates a new register before operation.
     *
     * @param serviceKey the key of the service to register before.
     * @param service the service to register.
     * @param filters the filters to apply to the service.
     * @param <Context> the context type.
     * @param <Result> the result type.
     * @return a new register before operation.
     */
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

    /**
     * Creates a new register before operation.
     *
     * @param serviceKey the key of the service to register before.
     * @param service the service to register.
     * @param <Context> the context type.
     * @param <Result> the result type.
     * @return a new register before operation.
     */
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

    /**
     * Creates a new register after operation.
     *
     * @param serviceKey the key of the service to register after.
     * @param service the service to register.
     * @param filters the filters to apply to the service.
     * @param <Context> the context type.
     * @param <Result> the result type.
     * @return a new register after operation.
     */
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

    /**
     * Creates a new register after operation.
     *
     * @param serviceKey the key of the service to register after.
     * @param service the service to register.
     * @param <Context> the context type.
     * @param <Result> the result type.
     * @return a new register after operation.
     */
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

    /**
     * Creates a new replace operation.
     *
     * @param serviceKey the key of the service to replace.
     * @param service the service to replace with.
     * @param filters the filters to apply to the service.
     * @param <Context> the context type.
     * @param <Result> the result type.
     * @return a new replace operation.
     */
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

    /**
     * Creates a new replace operation.
     *
     * @param serviceKey the key of the service to replace.
     * @param service the service to replace with.
     * @param <Context> the context type.
     * @param <Result> the result type.
     * @return a new replace operation.
     */
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

    static <Context, Result> Implementation<Context, Result> decorate(
        final ConsumerService<Context> service,
        final Collection<Predicate<Context>> filters
    ) {
        return new Decorate<>(service, filters);
    }

    static <Context, Result> Implementation<Context, Result> decorate(
        final ConsumerService<Context> service
    ) {
        return Implementation.decorate(Preconditions.argumentNotNull(service, "service"), null);
    }

    static <Context> Implementation<Context, ConsumerService.State> decorateConsumer(
        final ConsumerService<Context> service,
        final Collection<Predicate<Context>> filters
    ) {
        return new DecorateConsumer<>(service, filters);
    }

    static <Context> Implementation<Context, ConsumerService.State> decorateConsumer(
        final ConsumerService<Context> service
    ) {
        return Implementation.decorateConsumer(
            Preconditions.argumentNotNull(service, "service"),
            null
        );
    }

    /**
     * Handles the operation by applying it to the given repository.
     *
     * @param repository the repository to apply the operation to.
     */
    void handle(ServiceRepository<Context, Result> repository);
}
