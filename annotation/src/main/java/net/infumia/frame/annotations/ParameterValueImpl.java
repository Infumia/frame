package net.infumia.frame.annotations;

import java.lang.reflect.Parameter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class ParameterValueImpl implements ParameterValue {

    private final Parameter parameter;
    private final Object value;
    private final ParameterDescriptor descriptor;

    ParameterValueImpl(
        @NotNull final Parameter parameter,
        @Nullable final Object value,
        @Nullable final ParameterDescriptor descriptor
    ) {
        this.parameter = parameter;
        this.value = value;
        this.descriptor = descriptor;
    }

    ParameterValueImpl(@NotNull final Parameter parameter, @Nullable final Object value) {
        this(parameter, value, null);
    }

    @NotNull
    @Override
    public Parameter parameter() {
        return this.parameter;
    }

    @Nullable
    @Override
    public Object value() {
        return this.value;
    }

    @Nullable
    @Override
    public ParameterDescriptor descriptor() {
        return this.descriptor;
    }
}
