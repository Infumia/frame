package net.infumia.frame.injector;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

public interface AnnotationAccessor {
    static AnnotationAccessor empty() {
        return AnnotationAccessorEmpty.INSTANCE;
    }

    static AnnotationAccessor of(final AnnotatedElement element) {
        return new AnnotationAccessorAnnotated(element);
    }

    static AnnotationAccessor of(final AnnotationAccessor... accessors) {
        return new AnnotationAccessorMultiDelegate(accessors);
    }

    <A extends Annotation> A annotation(Class<A> cls);

    Collection<Annotation> annotations();
}
