package net.infumia.frame.injector;

import io.leangen.geantyref.GenericTypeReflector;
import io.leangen.geantyref.TypeToken;

public interface InjectionRequest<C> {
    static <C> InjectionRequest<C> of(
        final TypeToken<?> injectedType,
        final C context,
        final AnnotationAccessor annotationAccessor
    ) {
        return new InjectionRequestImpl<>(injectedType, context, annotationAccessor);
    }

    TypeToken<?> injectedType();

    default Class<?> injectedClass() {
        return GenericTypeReflector.erase(this.injectedType().getType());
    }

    C context();

    AnnotationAccessor annotationAccessor();
}
