package net.infumia.inv.api.service;

public interface Cancellable {
    boolean cancelled();

    void cancelled(boolean cancelled);
}
