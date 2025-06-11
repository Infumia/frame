package net.infumia.frame.annotations.decorator;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.infumia.frame.annotations.ViewDecorator;
import net.infumia.frame.annotations.ViewDecoratorFactory;
import org.jetbrains.annotations.NotNull;

public final class ViewDecoratorFactoryImpl implements ViewDecoratorFactory {

    private final Map<Class<? extends Annotation>, ViewDecorator<?>> decorators = new HashMap<>();

    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    public <A extends Annotation> Optional<ViewDecorator<A>> create(
        @NotNull final Class<A> annotation
    ) {
        return Optional.ofNullable((ViewDecorator<A>) this.decorators.get(annotation));
    }

    public <A extends Annotation> void register(
        @NotNull final Class<A> annotation,
        @NotNull final ViewDecorator<A> decorator
    ) {
        this.decorators.put(annotation, decorator);
    }
}
