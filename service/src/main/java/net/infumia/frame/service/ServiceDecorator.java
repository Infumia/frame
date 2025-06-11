package net.infumia.frame.service;

import java.util.concurrent.CompletableFuture;

public final class ServiceDecorator<Context, Result> implements Service<Context, Result> {

    private final Service<Context, Result> origin;
    private final ConsumerService<Context> decorator;

    public ServiceDecorator(
        final Service<Context, Result> origin,
        final ConsumerService<Context> decorator
    ) {
        this.origin = origin;
        this.decorator = decorator;
    }

    @Override
    public CompletableFuture<Result> handle(final Context context) {
        return this.origin.handle(context).thenApply(result -> {
                this.decorator.accept(context);
                return result;
            });
    }

    @Override
    public String key() {
        return this.origin.key();
    }
}
