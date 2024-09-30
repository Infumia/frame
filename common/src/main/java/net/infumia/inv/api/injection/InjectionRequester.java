package net.infumia.inv.api.injection;

import io.leangen.geantyref.TypeToken;
import java.util.concurrent.CompletableFuture;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface InjectionRequester<C> {
    @NotNull
    static <C> InjectionRequester<C> create(@NotNull final InjectionServicePipeline<C> pipeline) {
        return new InjectionRequesterImpl<>(pipeline);
    }

    @NotNull
    <T> CompletableFuture<@Nullable T> request(
        @NotNull Class<T> cls,
        @NotNull C context,
        @NotNull AnnotationAccessor annotationAccessor
    );

    @NotNull
    <T> CompletableFuture<@Nullable T> request(
        @NotNull TypeToken<T> type,
        @NotNull C context,
        @NotNull AnnotationAccessor annotationAccessor
    );
}
