package net.infumia.frame.annotations;

import java.lang.reflect.Parameter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ParameterValue {
    @NotNull
    static ParameterValue of(@NotNull final Parameter parameter, @Nullable final Object value) {
        return new ParameterValueImpl(parameter, value);
    }

    @NotNull
    static ParameterValue of(
        @NotNull final Parameter parameter,
        @Nullable final Object value,
        @Nullable final ParameterDescriptor descriptor
    ) {
        return new ParameterValueImpl(parameter, value, descriptor);
    }

    @NotNull
    Parameter parameter();

    @Nullable
    Object value();

    @Nullable
    ParameterDescriptor descriptor();
}
