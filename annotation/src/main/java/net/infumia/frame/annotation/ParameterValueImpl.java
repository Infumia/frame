package net.infumia.frame.annotation;

import java.lang.reflect.Parameter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
final class ParameterValueImpl implements ParameterValue {

    @NotNull
    private final Parameter parameter;

    @Nullable
    private final Object value;

    @Nullable
    private final ParameterDescriptor descriptor;

    public ParameterValueImpl(@NotNull final Parameter parameter, @Nullable final Object value) {
        this(parameter, value, null);
    }
}
