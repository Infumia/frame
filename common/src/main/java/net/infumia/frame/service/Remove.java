package net.infumia.frame.service;

import org.jetbrains.annotations.NotNull;

final class Remove<Context, Result> implements Implementation<Context, Result> {

    private final String serviceKey;

    Remove(@NotNull final String serviceKey) {
        this.serviceKey = serviceKey;
    }

    @Override
    public void handle(@NotNull final ServiceRepository<Context, Result> repository) {
        repository.implementations.removeIf(wrapper ->
            wrapper.implementation.key().equals(this.serviceKey)
        );
    }
}
