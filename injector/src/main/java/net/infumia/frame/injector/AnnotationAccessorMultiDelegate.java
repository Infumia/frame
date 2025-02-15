package net.infumia.frame.injector;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;

final class AnnotationAccessorMultiDelegate implements AnnotationAccessor {

    private final AnnotationAccessor[] accessors;

    AnnotationAccessorMultiDelegate(final AnnotationAccessor[] accessors) {
        this.accessors = Objects.requireNonNull(accessors, "accessors");
    }

    @Override
    public <A extends Annotation> A annotation(final Class<A> cls) {
        Objects.requireNonNull(cls, "cls");

        return Arrays.stream(this.accessors)
            .map(accessor -> accessor.annotation(cls))
            .filter(Objects::nonNull)
            .findFirst()
            .orElse(null);
    }

    @Override
    public Collection<Annotation> annotations() {
        return Collections.unmodifiableCollection(
            Arrays.stream(this.accessors)
                .flatMap(accessor -> accessor.annotations().stream())
                .collect(Collectors.toCollection(LinkedList::new))
        );
    }
}
