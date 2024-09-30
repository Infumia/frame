package net.infumia.frame.injection;

import io.leangen.geantyref.TypeToken;
import java.util.concurrent.CompletableFuture;
import net.infumia.frame.injector.InjectionRequest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class InjectionRequesterImpl<C> implements InjectionRequester<C> {

    @NotNull
    private final InjectionServicePipeline<C> pipeline;

    InjectionRequesterImpl(@NotNull final InjectionServicePipeline<C> pipeline) {
        this.pipeline = pipeline;
    }

    @NotNull
    @Override
    public <T> CompletableFuture<@Nullable T> request(
        @NotNull final Class<T> cls,
        @NotNull final C context,
        @NotNull final AnnotationAccessor annotationAccessor
    ) {
        return this.request(TypeToken.get(cls), context, annotationAccessor);
    }

    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    public <T> CompletableFuture<@Nullable T> request(
        @NotNull final TypeToken<T> type,
        @NotNull final C context,
        @NotNull final AnnotationAccessor annotationAccessor
    ) {
        final InjectionRequest<C> request = InjectionRequest.of(type, context, annotationAccessor);
        return this.pipeline.completeWith(request).thenApply(rawResult -> {
                if (rawResult == null) {
                    return null;
                }
                final Class<?> injectedClass = request.injectedClass();
                if (injectedClass.isInstance(rawResult)) {
                    return (T) rawResult;
                } else {
                    throw new IllegalArgumentException(
                        String.format(
                            "Injector returned type %s which is not an instance of %s",
                            rawResult.getClass().getName(),
                            injectedClass.getName()
                        )
                    );
                }
            });
    }
}
