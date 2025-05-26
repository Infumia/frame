package net.infumia.frame.injector;

import io.leangen.geantyref.TypeToken;
import java.util.concurrent.CompletableFuture;
import net.infumia.frame.Preconditions;

final class InjectionRequesterImpl<C> implements InjectionRequester<C> {

    private final InjectionServicePipeline<C> pipeline;

    InjectionRequesterImpl(final InjectionServicePipeline<C> pipeline) {
        this.pipeline = Preconditions.argumentNotNull(pipeline, "pipeline");
    }

    @Override
    public <T> CompletableFuture<T> request(
        final Class<T> cls,
        final C context,
        final AnnotationAccessor annotationAccessor
    ) {
        Preconditions.argumentNotNull(cls, "cls");
        Preconditions.argumentNotNull(context, "context");
        Preconditions.argumentNotNull(annotationAccessor, "annotationAccessor");

        return this.request(TypeToken.get(cls), context, annotationAccessor);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> CompletableFuture<T> request(
        final TypeToken<T> type,
        final C context,
        final AnnotationAccessor annotationAccessor
    ) {
        Preconditions.argumentNotNull(type, "type");
        Preconditions.argumentNotNull(context, "context");
        Preconditions.argumentNotNull(annotationAccessor, "annotationAccessor");

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
