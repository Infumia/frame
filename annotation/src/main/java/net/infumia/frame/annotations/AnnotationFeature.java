package net.infumia.frame.annotations;

import net.infumia.frame.Frame;
import net.infumia.frame.annotations.decorator.ViewDecoratorFactoryImpl;
import net.infumia.frame.annotations.decorator.view.ViewSize;
import net.infumia.frame.annotations.decorator.view.ViewSizeDecorator;
import net.infumia.frame.annotations.decorator.view.ViewTitle;
import net.infumia.frame.annotations.decorator.view.ViewTitleDecorator;
import net.infumia.frame.annotations.service.ServiceCreateAndProcessViews;
import net.infumia.frame.feature.Feature;
import net.infumia.frame.service.Implementation;
import org.jetbrains.annotations.NotNull;

public final class AnnotationFeature implements Feature {

    private final AnnotationProcessor annotationProcessor;
    private final ViewDecoratorFactoryImpl decoratorFactory;

    public AnnotationFeature() {
        this.decoratorFactory = new ViewDecoratorFactoryImpl();
        this.annotationProcessor = new AnnotationProcessor(this.decoratorFactory);
    }

    @NotNull
    public AnnotationProcessor annotationProcessor() {
        return this.annotationProcessor;
    }

    @NotNull
    public ViewDecoratorFactory decoratorFactory() {
        return this.decoratorFactory;
    }

    @NotNull
    @Override
    public String name() {
        return "annotations";
    }

    @Override
    public void onInstall(@NotNull final Frame frame) {
        this.decoratorFactory.register(ViewSize.class, new ViewSizeDecorator());
        this.decoratorFactory.register(ViewTitle.class, new ViewTitleDecorator());
        // TODO: Register more decorators

        frame
            .pipelines()
            .applyViewCreated(
                Implementation.register(new ServiceCreateAndProcessViews(this.annotationProcessor))
            );
    }

    @Override
    public void onUninstall(@NotNull final Frame frame) {
        // TODO: Unregister services and clear decorators
    }
}
