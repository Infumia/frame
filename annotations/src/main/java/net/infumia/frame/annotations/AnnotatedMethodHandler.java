package net.infumia.frame.annotations;

import io.leangen.geantyref.TypeToken;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import net.infumia.frame.annotations.exception.AnnotatedMethodHandlerInitiationException;
import net.infumia.frame.injection.AnnotationAccessor;
import net.infumia.frame.injection.InjectionRequester;
import net.infumia.frame.util.Preconditions;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AnnotatedMethodHandler<C> {

    protected final List<Parameter> parameters;
    protected final MethodHandle methodHandle;
    protected final AnnotationAccessor annotationAccessor;
    protected final InjectionRequester<C> injector;

    protected AnnotatedMethodHandler(
        @NotNull final Method method,
        @NotNull final Object instance,
        @NotNull final InjectionRequester<C> injector
    ) {
        try {
            this.parameters = Arrays.asList(method.getParameters());
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            this.methodHandle = MethodHandles.lookup().unreflect(method).bindTo(instance);
            this.annotationAccessor = AnnotationAccessor.of(method);
            this.injector = injector;
        } catch (final Exception exception) {
            throw new AnnotatedMethodHandlerInitiationException(exception);
        }
    }

    @Nullable
    @ApiStatus.OverrideOnly
    protected ParameterValue getParameterValue(
        @NotNull final C context,
        @NotNull final Parameter parameter
    ) {
        return null;
    }

    @NotNull
    @SuppressWarnings("unchecked")
    protected CompletableFuture<Collection<ParameterValue>> createParameterValues(
        @NotNull final C context
    ) {
        final CompletableFuture<@Nullable ParameterValue>[] futures =
            this.parameters.stream()
                .map(parameter -> this.createParameterValue(context, parameter))
                .toArray(CompletableFuture[]::new);

        return CompletableFuture.allOf(futures).thenApply(__ ->
            Arrays.stream(futures)
                .map(CompletableFuture::join)
                .collect(
                    Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList)
                )
        );
    }

    @NotNull
    private CompletableFuture<@Nullable ParameterValue> createParameterValue(
        @NotNull final C context,
        @NotNull final Parameter parameter
    ) {
        final ParameterValue contextualValue = this.getParameterValue(context, parameter);
        if (contextualValue != null) {
            return CompletableFuture.completedFuture(contextualValue);
        }
        return this.requestParameterValue(parameter, context);
    }

    @NotNull
    private CompletableFuture<ParameterValue> requestParameterValue(
        @NotNull final Parameter parameter,
        @NotNull final C context
    ) {
        return this.requestInjection(parameter, context).thenApply(value -> {
                Preconditions.argumentNotNull(
                    value,
                    "Could not create value for parameter '%s' of type '%s' in method '%s'",
                    parameter.getName(),
                    parameter.getType().getTypeName(),
                    this.methodHandle
                );
                if (parameter.getType() == String.class) {
                    return ParameterValue.of(parameter, parameter.getName());
                }
                return ParameterValue.of(parameter, value);
            });
    }

    @NotNull
    @SuppressWarnings("unchecked")
    private CompletableFuture<@Nullable Object> requestInjection(
        @NotNull final Parameter parameter,
        @NotNull final C context
    ) {
        return this.injector.request(
                (TypeToken<Object>) TypeToken.get(parameter.getParameterizedType()),
                context,
                AnnotationAccessor.of(AnnotationAccessor.of(parameter), this.annotationAccessor)
            );
    }
}
