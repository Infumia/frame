package net.infumia.frame.service.exception;

/**
 * An exception thrown when an error occurs within the service pipeline.
 */
public final class PipelineException extends RuntimeException {

    /**
     * Constructs a new {@link PipelineException} with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause the cause of the exception.
     */
    public PipelineException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
