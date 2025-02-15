package net.infumia.frame.injection;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class AnnotationAccessorEmpty implements AnnotationAccessor {

    static final AnnotationAccessor INSTANCE = new AnnotationAccessorEmpty();

    private AnnotationAccessorEmpty() {}

    @Nullable
    @Override
    public <A extends Annotation> A annotation(@NotNull final Class<A> cls) {
        return null;
    }

    @NotNull
    @Override
    public Collection<Annotation> annotations() {
        return Collections.emptyList();
    }
}
