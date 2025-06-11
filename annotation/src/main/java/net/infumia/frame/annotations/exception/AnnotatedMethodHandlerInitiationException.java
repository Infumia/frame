package net.infumia.frame.annotations.exception;

import org.jetbrains.annotations.NotNull;

public final class AnnotatedMethodHandlerInitiationException extends RuntimeException {

    public AnnotatedMethodHandlerInitiationException(@NotNull final Throwable cause) {
        super(cause);
    }
}
