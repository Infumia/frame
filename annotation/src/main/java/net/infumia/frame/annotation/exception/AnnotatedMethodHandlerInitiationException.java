package net.infumia.frame.annotation.exception;

import lombok.experimental.StandardException;
import net.infumia.frame.annotation.AnnotatedMethodHandler;

/**
 * Exception thrown when there is an error during the initiation of {@link AnnotatedMethodHandler}s.
 */
@StandardException
public final class AnnotatedMethodHandlerInitiationException extends RuntimeException {}
