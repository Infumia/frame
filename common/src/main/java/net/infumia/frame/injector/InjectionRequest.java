package net.infumia.frame.injector;

import io.leangen.geantyref.GenericTypeReflector;
import io.leangen.geantyref.TypeToken;
import net.infumia.frame.injection.AnnotationAccessor;
import org.jetbrains.annotations.NotNull;

public interface InjectionRequest<C> {
    @NotNull
    static <C> InjectionRequest<C> of(
        @NotNull final TypeToken<?> injectedType,
        @NotNull final C context,
        @NotNull final AnnotationAccessor annotationAccessor
    ) {
        return new InjectionRequestImpl<>(injectedType, context, annotationAccessor);
    }

    @NotNull
    TypeToken<?> injectedType();

    @NotNull
    default Class<?> injectedClass() {
        return GenericTypeReflector.erase(this.injectedType().getType());
    }

    @NotNull
    C context();

    @NotNull
    AnnotationAccessor annotationAccessor();
}
