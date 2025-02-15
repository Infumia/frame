package net.infumia.frame.injector;

import io.leangen.geantyref.TypeToken;
import java.util.Objects;

final class InjectionRequestImpl<C> implements InjectionRequest<C> {

    private final TypeToken<?> injectedType;
    private final C context;
    private final AnnotationAccessor annotationAccessor;

    InjectionRequestImpl(
        final TypeToken<?> injectedType,
        final C context,
        final AnnotationAccessor annotationAccessor
    ) {
        this.injectedType = Objects.requireNonNull(injectedType, "injectedType");
        this.context = Objects.requireNonNull(context, "context");
        this.annotationAccessor = Objects.requireNonNull(annotationAccessor, "annotationAccessor");
    }

    @Override
    public TypeToken<?> injectedType() {
        return this.injectedType;
    }

    @Override
    public C context() {
        return this.context;
    }

    @Override
    public AnnotationAccessor annotationAccessor() {
        return this.annotationAccessor;
    }
}
