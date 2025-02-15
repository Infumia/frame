package net.infumia.frame.service;

public interface Cancellable {
    boolean cancelled();

    void cancelled(boolean cancelled);
}
