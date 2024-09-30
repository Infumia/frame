package net.infumia.inv.api.injection;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class AnnotationAccessorAnnotated implements AnnotationAccessor {

    @NotNull
    private final AnnotatedElement element;

    AnnotationAccessorAnnotated(@NotNull final AnnotatedElement element) {
        this.element = element;
    }

    @Nullable
    @Override
    public <A extends Annotation> A annotation(@NotNull final Class<A> cls) {
        return this.element.getAnnotation(cls);
    }

    @NotNull
    @Override
    public Collection<Annotation> annotations() {
        return Collections.unmodifiableCollection(Arrays.asList(this.element.getAnnotations()));
    }
}
