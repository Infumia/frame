package net.infumia.frame.annotations;

import java.lang.annotation.Annotation;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public interface ViewDecoratorFactory {
    @NotNull
    <A extends Annotation> Optional<ViewDecorator<A>> create(@NotNull Class<A> annotation);
}
