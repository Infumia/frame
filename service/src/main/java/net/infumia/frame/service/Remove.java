package net.infumia.frame.service;

final class Remove<Context, Result> implements Implementation<Context, Result> {

    private final String serviceKey;

    Remove(final String serviceKey) {
        this.serviceKey = serviceKey;
    }

    @Override
    public void handle(final ServiceRepository<Context, Result> repository) {
        repository.implementations.removeIf(wrapper ->
            wrapper.implementation.key().equals(this.serviceKey)
        );
    }
}
