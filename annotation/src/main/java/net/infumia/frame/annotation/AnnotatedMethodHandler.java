package net.infumia.frame.annotation;

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
import net.infumia.frame.annotation.exception.AnnotatedMethodHandlerInitiationException;
import net.infumia.frame.api.injection.InjectionRequester;
import net.infumia.frame.api.util.Preconditions;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Abstract class that handles annotated methods for dependency injection.
 *
 * @param <C> the type of context used for method invocation.
 */
public abstract class AnnotatedMethodHandler<C> {

    /**
     * Parameters of the method being handled.
     */
    @NotNull
    protected final List<Parameter> parameters;

    /**
     * MethodHandle for invoking the method.
     */
    @NotNull
    protected final MethodHandle methodHandle;

    /**
     * Accessor for retrieving annotations from the method.
     */
    @NotNull
    protected final AnnotationAccessor annotationAccessor;

    /**
     * Injector for managing dependencies.
     */
    @NotNull
    protected final InjectionRequester<C> injector;

    /**
     * Ctor.
     *
     * @param method   the method to be handled.
     * @param instance the instance on which the method will be invoked.
     * @param injector the injector used to provide dependencies.
     * @throws AnnotatedMethodHandlerInitiationException if the method cannot be accessed or unreflect.
     */
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

    /**
     * Retrieves the value for a specific parameter in the given context.
     * <p>
     * Returning {@code null} skip the contextual parameters values, instead {@link #injector} will be used.
     *
     * @param context   the context in which the method is being invoked.
     * @param parameter the parameter whose value is to be retrieved.
     * @return the value of the parameter.
     */
    @Nullable
    @ApiStatus.OverrideOnly
    protected ParameterValue getParameterValue(
        @NotNull final C context,
        @NotNull final Parameter parameter
    ) {
        return null;
    }

    /**
     * Creates a list of parameter values based on the current context.
     *
     * @param context the context in which the method is being invoked.
     * @return an unmodifiable list of parameter values.
     */
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
