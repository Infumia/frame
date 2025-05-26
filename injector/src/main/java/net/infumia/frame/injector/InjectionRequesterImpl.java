package net.infumia.frame.injector;

import io.leangen.geantyref.TypeToken;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

final class InjectionRequesterImpl<C> implements InjectionRequester<C> {

    private final InjectionServicePipeline<C> pipeline;

    InjectionRequesterImpl(final InjectionServicePipeline<C> pipeline) {
        this.pipeline = Objects.requireNonNull(pipeline, "pipeline");
    }

    @Override
    public <T> CompletableFuture<T> request(
        final Class<T> cls,
        final C context,
        final AnnotationAccessor annotationAccessor
    ) {
        Objects.requireNonNull(cls, "cls");
        Objects.requireNonNull(context, "context");
        Objects.requireNonNull(annotationAccessor, "annotationAccessor");

        return this.request(TypeToken.get(cls), context, annotationAccessor);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> CompletableFuture<T> request(
        final TypeToken<T> type,
        final C context,
        final AnnotationAccessor annotationAccessor
    ) {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(context, "context");
        Objects.requireNonNull(annotationAccessor, "annotationAccessor");

        final InjectionRequest<C> request = InjectionRequest.of(type, context, annotationAccessor);
        return this.pipeline.completeDirect(request).thenApply(rawResult -> {
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
