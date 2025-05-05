package net.infumia.frame.injector;

import io.leangen.geantyref.TypeToken;
import java.util.concurrent.CompletableFuture;

public interface InjectionRequester<C> {
    static <C> InjectionRequester<C> create(final InjectionServicePipeline<C> pipeline) {
        return new InjectionRequesterImpl<>(pipeline);
    }

    <T> CompletableFuture<T> request(
        Class<T> cls,
        C context,
        AnnotationAccessor annotationAccessor
    );

    <T> CompletableFuture<T> request(
        TypeToken<T> type,
        C context,
        AnnotationAccessor annotationAccessor
    );
}
