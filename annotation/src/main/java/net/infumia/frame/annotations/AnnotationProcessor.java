package net.infumia.frame.annotations;

import java.lang.annotation.Annotation;
import net.infumia.frame.view.View;
import org.jetbrains.annotations.NotNull;

public final class AnnotationProcessor {

    private final ViewDecoratorFactory decoratorFactory;

    public AnnotationProcessor(@NotNull final ViewDecoratorFactory decoratorFactory) {
        this.decoratorFactory = decoratorFactory;
    }

    public void process(@NotNull final View view) {
        final Class<?> viewClass = view.instance().getClass();
        for (final Annotation annotation : viewClass.getAnnotations()) {
            this.processAnnotation(view, annotation);
        }
    }

    private <A extends Annotation> void processAnnotation(
        @NotNull final View view,
        @NotNull final A annotation
    ) {
        this.decoratorFactory.create(annotation.annotationType()).ifPresent(decorator -> {
                @SuppressWarnings("unchecked")
                final ViewDecorator<A> typedDecorator = (ViewDecorator<A>) decorator;
                typedDecorator.decorate(view, annotation);
            });
    }
}
