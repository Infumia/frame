package net.infumia.frame.service;

/**
 * An interface for cancellable operations.
 */
public interface Cancellable {
    /**
     * Checks if the operation is cancelled.
     *
     * @return {@code true} if the operation is cancelled, {@code false} otherwise.
     */
    boolean cancelled();

    /**
     * Sets the cancelled state of the operation.
     *
     * @param cancelled {@code true} to cancel the operation, {@code false} otherwise.
     */
    void cancelled(boolean cancelled);
}
