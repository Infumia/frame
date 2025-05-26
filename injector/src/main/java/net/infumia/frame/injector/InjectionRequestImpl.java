package net.infumia.frame.injector;

import io.leangen.geantyref.TypeToken;
import net.infumia.frame.Preconditions;

final class InjectionRequestImpl<C> implements InjectionRequest<C> {

    private final TypeToken<?> injectedType;
    private final C context;
    private final AnnotationAccessor annotationAccessor;

    InjectionRequestImpl(
        final TypeToken<?> injectedType,
        final C context,
        final AnnotationAccessor annotationAccessor
    ) {
        this.injectedType = Preconditions.argumentNotNull(injectedType, "injectedType");
        this.context = Preconditions.argumentNotNull(context, "context");
        this.annotationAccessor = Preconditions.argumentNotNull(
            annotationAccessor,
            "annotationAccessor"
        );
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
