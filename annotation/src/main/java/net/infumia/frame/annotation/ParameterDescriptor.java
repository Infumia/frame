package net.infumia.frame.annotation;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a descriptor for a parameter.
 */
public interface ParameterDescriptor {
    /**
     * Retrieves the name of the parameter.
     *
     * @return the name of the parameter.
     */
    @NotNull
    String name();
}
