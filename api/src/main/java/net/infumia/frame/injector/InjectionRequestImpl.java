package net.infumia.frame.injector;

import io.leangen.geantyref.TypeToken;
import net.infumia.frame.injection.AnnotationAccessor;
import org.jetbrains.annotations.NotNull;

final class InjectionRequestImpl<C> implements InjectionRequest<C> {

    @NotNull
    private final TypeToken<?> injectedType;

    @NotNull
    private final C context;

    @NotNull
    private final AnnotationAccessor annotationAccessor;

    InjectionRequestImpl(
        @NotNull final TypeToken<?> injectedType,
        @NotNull final C context,
        @NotNull final AnnotationAccessor annotationAccessor
    ) {
        this.injectedType = injectedType;
        this.context = context;
        this.annotationAccessor = annotationAccessor;
    }

    @NotNull
    @Override
    public TypeToken<?> injectedType() {
        return this.injectedType;
    }

    @NotNull
    @Override
    public C context() {
        return this.context;
    }

    @NotNull
    @Override
    public AnnotationAccessor annotationAccessor() {
        return this.annotationAccessor;
    }
}
