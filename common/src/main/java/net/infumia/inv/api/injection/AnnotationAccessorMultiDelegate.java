package net.infumia.inv.api.injection;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class AnnotationAccessorMultiDelegate implements AnnotationAccessor {

    @NotNull
    private final AnnotationAccessor@NotNull[] accessors;

    AnnotationAccessorMultiDelegate(final @NotNull AnnotationAccessor@NotNull[] accessors) {
        this.accessors = accessors;
    }

    @Nullable
    @Override
    public <A extends Annotation> A annotation(@NotNull final Class<A> cls) {
        return Arrays.stream(this.accessors)
            .map(accessor -> accessor.annotation(cls))
            .filter(Objects::nonNull)
            .findFirst()
            .orElse(null);
    }

    @NotNull
    @Override
    public Collection<Annotation> annotations() {
        return Collections.unmodifiableCollection(
            Arrays.stream(this.accessors)
                .flatMap(accessor -> accessor.annotations().stream())
                .collect(Collectors.toCollection(LinkedList::new))
        );
    }
}
