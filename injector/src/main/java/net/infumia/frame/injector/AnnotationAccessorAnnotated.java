package net.infumia.frame.injector;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

final class AnnotationAccessorAnnotated implements AnnotationAccessor {

    private final AnnotatedElement element;

    AnnotationAccessorAnnotated(final AnnotatedElement element) {
        this.element = Objects.requireNonNull(element, "element");
    }

    @Override
    public <A extends Annotation> A annotation(final Class<A> cls) {
        Objects.requireNonNull(cls, "cls");

        return this.element.getAnnotation(cls);
    }

    @Override
    public Collection<Annotation> annotations() {
        return Collections.unmodifiableCollection(Arrays.asList(this.element.getAnnotations()));
    }
}
