package net.infumia.frame.injector;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import net.infumia.frame.Preconditions;

final class AnnotationAccessorAnnotated implements AnnotationAccessor {

    private final AnnotatedElement element;

    AnnotationAccessorAnnotated(final AnnotatedElement element) {
        this.element = Preconditions.argumentNotNull(element, "element");
    }

    @Override
    public <A extends Annotation> A annotation(final Class<A> cls) {
        Preconditions.argumentNotNull(cls, "cls");

        return this.element.getAnnotation(cls);
    }

    @Override
    public Collection<Annotation> annotations() {
        return Collections.unmodifiableCollection(Arrays.asList(this.element.getAnnotations()));
    }
}
