package net.infumia.frame.annotation;

import java.lang.reflect.Parameter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a value associated with a parameter.
 */
public interface ParameterValue {
    /**
     * Creates a new instance of {@link ParameterValue} with the specified parameter and value.
     *
     * @param parameter the parameter associated with this value.
     * @param value     the value associated with the parameter.
     * @return a new {@link ParameterValue} instance.
     */
    @NotNull
    static ParameterValue of(@NotNull final Parameter parameter, @Nullable final Object value) {
        return new ParameterValueImpl(parameter, value);
    }

    /**
     * Creates a new instance of {@link ParameterValue} with the specified parameter, value, and descriptor.
     *
     * @param parameter  the parameter associated with this value.
     * @param value      the value associated with the parameter.
     * @param descriptor an optional descriptor for the parameter.
     * @return a new {@link ParameterValue} instance.
     */
    @NotNull
    static ParameterValue of(
        @NotNull final Parameter parameter,
        @Nullable final Object value,
        @Nullable final ParameterDescriptor descriptor
    ) {
        return new ParameterValueImpl(parameter, value, descriptor);
    }

    /**
     * Returns the parameter associated with this value.
     *
     * @return the parameter.
     */
    @NotNull
    Parameter parameter();

    /**
     * Returns the value associated with the parameter.
     *
     * @return the value.
     */
    @Nullable
    Object value();

    /**
     * Returns the descriptor for the parameter.
     *
     * @return the descriptor.
     */
    @Nullable
    ParameterDescriptor descriptor();
}
