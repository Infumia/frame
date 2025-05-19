package net.infumia.frame.service.exception;

import org.jetbrains.annotations.NotNull;

public final class PipelineException extends RuntimeException {

    public PipelineException(@NotNull final String message, @NotNull final Throwable cause) {
        super(message, cause);
    }
}
