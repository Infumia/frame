package net.infumia.frame.service;

import java.util.concurrent.CompletableFuture;

public final class ServiceDecoratorConsumer<Context> implements ConsumerService<Context> {

    private final Service<Context, ConsumerService.State> origin;
    private final ConsumerService<Context> decorator;

    public ServiceDecoratorConsumer(
        final Service<Context, ConsumerService.State> origin,
        final ConsumerService<Context> decorator
    ) {
        this.origin = origin;
        this.decorator = decorator;
    }

    @Override
    public CompletableFuture<ConsumerService.State> handle(final Context context) {
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
