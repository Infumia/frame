package net.infumia.frame.injector;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;

final class AnnotationAccessorEmpty implements AnnotationAccessor {

    static final AnnotationAccessor INSTANCE = new AnnotationAccessorEmpty();

    private AnnotationAccessorEmpty() {}

    @Override
    public <A extends Annotation> A annotation(final Class<A> cls) {
        return null;
    }

    @Override
    public Collection<Annotation> annotations() {
        return Collections.emptyList();
    }
}
