package net.infumia.inv.api.injection;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface AnnotationAccessor {
    @NotNull
    static AnnotationAccessor empty() {
        return AnnotationAccessorEmpty.INSTANCE;
    }

    @NotNull
    static AnnotationAccessor of(@NotNull final AnnotatedElement element) {
        return new AnnotationAccessorAnnotated(element);
    }

    @NotNull
    static AnnotationAccessor of(@NotNull final AnnotationAccessor @NotNull... accessors) {
        return new AnnotationAccessorMultiDelegate(accessors);
    }

    @Nullable
    <A extends Annotation> A annotation(@NotNull Class<A> cls);

    @NotNull
    Collection<Annotation> annotations();
}
